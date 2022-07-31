package com.gw.job.sample.dao

import com.gw.job.sample.entity.doma.Employee
import com.gw.job.sample.entity.enums.DepartmentCode
import com.gw.job.sample.entity.selector.EmployeeListSelector
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito
import org.seasar.doma.boot.autoconfigure.DomaAutoConfiguration
import org.seasar.doma.jdbc.SelectOptions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import
import spock.lang.Specification

import javax.sql.DataSource
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Statement
import java.time.LocalDate

import static org.mockito.ArgumentMatchers.eq

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DomaAutoConfiguration.class)
@ComponentScan
class EmployeeDaoSpec extends Specification {

    @Autowired
    EmployeeDao target

    @Autowired
    DataSource dataSource

    @Captor
    ArgumentCaptor<String> captor

    def preparedStatement = Mock(PreparedStatement)
    def capturedValues = [:] as Map<Integer, String>
    def replaceRegex = "[ \\t\\n]+"

    def setup() {
        capturedValues.clear()
        def connection = dataSource.getConnection()

        // preparedStatementの各セッターのmock化
        // バインドされるパラメータをcapturedValuesにMapで詰める
        preparedStatement.setString(*_) >> { index, val ->
            capturedValues.put(index, val)
        }
        preparedStatement.setInt(*_) >> { index, val ->
            capturedValues.put(index, val as String)
        }
        preparedStatement.setLong(*_) >> { index, val ->
            capturedValues.put(index, val as String)
        }
        preparedStatement.setObject(*_) >> { index, val ->
            capturedValues.put(index, val as String)
        }
        preparedStatement.setDate(*_) >> { index, val ->
            capturedValues.put(index, val as String)
        }
        preparedStatement.setNull(*_) >> { index, val ->
            capturedValues.put(index, null)
        }

        def resultSet = Mock(ResultSet)
        preparedStatement.executeQuery() >> resultSet

        def generatedResultSet = Mock(ResultSet)
        generatedResultSet.next() >> true
        preparedStatement.getGeneratedKeys() >> generatedResultSet

        Mockito.doReturn(preparedStatement).when(connection).prepareStatement(captor.capture())
        Mockito.doReturn(preparedStatement).when(connection).prepareStatement(
                captor.capture(), eq(Statement.RETURN_GENERATED_KEYS)
        )
    }

    /**
     * 各テストケース実行後に実行される
     */
    def cleanup() {
        Mockito.reset(dataSource.getConnection())
    }

    def "findByEmployeeId"() {
        given:
        def employeeId = 1L
        def expectedSql = """
                          | ${CapturedSqlTemplate.EMPLOYEE_ALL_COLUMN}
                          | FROM
                          |    employee
                          | WHERE
                          |    employee_id = ?
                          """
                .stripMargin()
                .replaceAll(replaceRegex, " ")
                .strip()

        when:
        target.findByEmployeeId(employeeId)

        then:
        verifyPreparedStatement() // SQLの呼び出し回数
        capturedSql() == expectedSql // 発行されたSQL
        capturedParams() == ["1"] // SQLに動的にバインドされる値(?の部分)
    }

    def "findWithOptions オプション未設定の場合IDのみ指定される"() {
        given:
        def employeeId = 1L
        def expectedSql = """
                          | ${CapturedSqlTemplate.EMPLOYEE_ALL_COLUMN}
                          | FROM
                          |    employee
                          | WHERE
                          |    employee_id = ?
                          """
                .stripMargin()
                .replaceAll(replaceRegex, " ")
                .strip()

        when:
        target.findWithOptions(employeeId, SelectOptions.get())

        then:
        verifyPreparedStatement()
        capturedSql() == expectedSql
        capturedParams() == ["1"]
    }

    def "findWithOptions 悲観ロック用のoption設定"() {
        given:
        def employeeId = 1L
        def options = SelectOptions.get().forUpdate()
        def expectedSql = """
                          | ${CapturedSqlTemplate.EMPLOYEE_ALL_COLUMN}
                          | FROM
                          |    employee
                          | WHERE
                          |    employee_id = ?
                          | for update
                          """
                .stripMargin()
                .replaceAll(replaceRegex, " ")
                .strip()

        when:
        target.findWithOptions(employeeId, options)

        then:
        verifyPreparedStatement()
        capturedSql() == expectedSql
        capturedParams() == ["1"]
    }

    def "search 検索条件未設定"() {
        given:
        def selector = new EmployeeListSelector(
                employeeIds: [],
                departmentCodes: [],
                entryDateFrom: null,
                entryDateTo: null
        )
        def options = SelectOptions.get()
        def expectedSql = """
                          | ${CapturedSqlTemplate.EMPLOYEE_ALL_COLUMN}
                          | FROM
                          |    employee
                          | ORDER BY
                          |    employee_id
                          """
                .stripMargin()
                .replaceAll(replaceRegex, " ")
                .strip()

        when:
        target.search(selector, options)

        then:
        verifyPreparedStatement()
        capturedSql() == expectedSql
        capturedParams() == []
    }

    def "search 検索条件全設定"() {
        given:
        def selector = new EmployeeListSelector(
                employeeIds: [1L, 2L],
                departmentCodes: [3L, 4L],
                entryDateFrom: LocalDate.of(2022, 7, 30),
                entryDateTo: LocalDate.of(2022, 8, 1)

        )
        def options = SelectOptions.get().offset(0).limit(100)
        def expectedSql = """
                          | ${CapturedSqlTemplate.EMPLOYEE_ALL_COLUMN}
                          | FROM
                          |    employee
                          | WHERE
                          |    employee_id IN (?, ?)
                          | AND
                          |    department_code IN (?, ?)
                          | AND
                          |    entry_date >= ?
                          | AND
                          |    entry_date <= ?
                          | ORDER BY
                          |    employee_id
                          | limit 0, 100
                          """
                .stripMargin()
                .replaceAll(replaceRegex, " ")
                .strip()

        when:
        target.search(selector, options)

        then:
        verifyPreparedStatement()
        capturedSql() == expectedSql
        capturedParams() == [
                "1", "2",
                "3", "4",
                "2022-07-30",
                "2022-08-01"
        ]
    }

    def "insert 全パラメータ設定"() {
        given:
        def expectedDepartmentCode = DepartmentCode.AFFAIRS
        def employee = new Employee(
                employeeId: null,
                lastName: "テスト",
                firstName: "太郎",
                entryDate: LocalDate.of(2022, 7, 1),
                departmentCode: expectedDepartmentCode,
                createdBy: "test",
                updatedBy: "test"
        )

        def expectedSql = "insert into employee" +
                " (last_name, first_name, entry_date, department_code, created_by, updated_by)" +
                " values (?, ?, ?, ?, ?, ?)"

        when:
        target.insert(employee)

        then:
        verifyPreparedStatementAutoGeneratedKey()
        capturedSql() == expectedSql
        capturedParams() == [
                "テスト",
                "太郎",
                "2022-07-01",
                expectedDepartmentCode.getValue() as String,
                "test",
                "test"
        ]
    }

    def "update 全パラメータ設定"() {
        given:
        def expectedDepartmentCode = DepartmentCode.AFFAIRS
        def employee = new Employee(
                employeeId: 1L,
                lastName: "テスト",
                firstName: "太郎",
                entryDate: LocalDate.of(2022, 7, 1),
                departmentCode: expectedDepartmentCode,
                updatedBy: "test"
        )

        def expectedSql = "update employee" +
                " set last_name = ?," +
                " first_name = ?," +
                " department_code = ?," +
                " updated_by = ?" +
                " where employee_id = ?"

        when:
        target.update(employee)

        then:
        verifyPreparedStatement()
        capturedSql() == expectedSql
        capturedParams() == [
                "テスト",
                "太郎",
                expectedDepartmentCode.getValue() as String,
                "test",
                "1"
        ]
    }

    def "deleteByEmployeeId"() {
        given:
        def employeeId = 1L
        def expectedSql = "DELETE" +
                " FROM employee" +
                " WHERE employee_id = ?"

        when:
        target.deleteByEmployeeId(employeeId)

        then:
        verifyPreparedStatement()
        capturedSql() == expectedSql
        capturedParams() == ["1"]
    }


    /**
     * SQLの実行回数を検証する
     * @param times 実行回数 default: 1
     * @return boolean SQLの実行回数が引数と一致しているか
     */
    private def verifyPreparedStatement(int times = 1) {
        def connection = dataSource.getConnection()
        Mockito.verify(connection, Mockito.times(times)).prepareStatement(captor.capture()) == null
    }

    /**
     * GeneratedKey指定のSQL実行回数を調べる
     * @param times 実行回数 default: 1
     * @return boolean SQLの実行回数が引数と一致しているか
     */
    private def verifyPreparedStatementAutoGeneratedKey(int times = 1) {
        def connection = dataSource.getConnection()
        Mockito.verify(connection, Mockito.times(times)).prepareStatement(
                captor.capture(), eq(Statement.RETURN_GENERATED_KEYS)
        ) == null
    }

    /**
     * 発行したSQLを返却する
     * タブ,改行は半角スペースにエスケープした上で返す
     * @return 補足したSQL
     */
    private def capturedSql() {
        captor.getValue().replaceAll("[ \t\n]+", " ")
    }

    /**
     * SQLにバインドされたパラメータを文字列のリストで返す
     * @return バインドされたパラメータ一覧
     */
    private def capturedParams() {
        capturedValues.values() as List<String>
    }
}
