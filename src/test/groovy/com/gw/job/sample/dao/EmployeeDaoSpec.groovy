package com.gw.job.sample.dao

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


    private def verifyPreparedStatement(int times = 1) {
        def connection = dataSource.getConnection()
        Mockito.verify(connection, Mockito.times(times)).prepareStatement(captor.capture()) == null
    }

    private def verifyPreparedStatementAutoGeneratedKey(int times = 1) {
        def connection = dataSource.getConnection()
        Mockito.verify(connection, Mockito.times(times)).prepareStatement(
                captor.capture(), eq(Statement.RETURN_GENERATED_KEYS)
        ) == null
    }

    private def capturedSql() {
        captor.getValue().replaceAll("[ \t\n]+", " ")
    }

    private def capturedParams() {
        capturedValues.values() as List<String>
    }
}
