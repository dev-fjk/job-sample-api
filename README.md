# job-sample-api

求人PJ 事前学習用API

## エンドポイント

各エンドポイントの詳細IF仕様は後述のSwaggerを参照

| uri | Controller | 説明   |
|----  |----   | ---   |
| resume/v1/  | ResumeController  | 職務経歴情報の操作を行う  |
| posted-company/v1/  | PostedCompanyController  | 企業への応募関連の操作を行う  |
| employee/v1/  | EmployeeController  | 社員情報の操作を行う ※サンプル実装用  |

## API仕様書(Swagger)

アプリケーションをビルドした後に以下のURLにアクセスする

- [ローカル(ビルドが必要)](http://localhost:8080/swagger-ui/index.html)

## DB定義

- [DB設計](https://namugahaku.atlassian.net/l/cp/DA8wbXq6)

※confluenceのアカウントと権限付与が必要なので連絡下さい

DDLは以下

- resources/h2/init.sql(h2)
- docker/mysql/initdb.d/1_schema.sql(MySQL)

## ブランチ運用

~~~
(1) develop/自身のユーザー名というブランチを作成
(2) developブランチからfeature/自身のユーザー名/機能名というブランチを作成
ex) feature/dev-fjk/add-resume
(3) feature -> developブランチへPRを作成する
(4) reviewerにdev-fjkを設定
~~~

# ローカルでの開発設定

以下のツールを準備してください

- JDK 11
- Groovy 3.0
- IDE(Eclipse or Intellij Ultimate Edition)
- Docker Desktop(任意)
- Maven(Eclipseの場合自前で用意必要かも・・？)

練習も兼ねてIntellij Ultimate Editionの使用を推奨します<br>
1ヶ月は無料 & JDKやmavenはIDE上からインストール可能であるため環境構築が楽です

## MySQLの準備(任意)

Dockerを使用する場合

- Docker Desktopをインストールしてローカル環境でDockerの環境構築を行う([Docker Desktop](https://www.docker.com/products/docker-desktop) )
- プロジェクトのルートディレクトリで 以下のコマンドを実行して mysqlのコンテナを立ち上げる(Win/Mac共通)

~~~
$ cd docker
$ docker-compose up -d --build
~~~ 

Docker推奨ですが、自前でMySQLを用意してもOKです。 その場合はapplication-local.ymlの接続情報を書き換えてください。<br>
面倒な場合はh2を使用してください。

## アプリケーションの実行

- デフォルトではMYSQLを指定して実行するようになっている
- MYSQLの準備が面倒な場合は spring.profile.activeに h2と指定して実行する

### プラグイン設定

OR Mapperとして Searar Domaを使用するため以下のプラグインを導入することを推奨します。

- Eclipse
    - http://doma.seasar.org/extension/doma_tools.html
- IntelliJ
    - https://plugins.jetbrains.com/plugin/7615-doma-support

### 一部のアノテーションでコンパイルエラーが出る場合

- Lombokというライブラリを使用して開発しているため、IDE側にLombokを認識させる設定が必要

IDE名 Lombok導入 とGoogleで調べればいくらでも記事が出てくるので調べてみてください

### CheckStyle設定(任意)

configパッケージ配下にcheckStyleを置いているので任意で使用する

- [Checkstyle 使い方メモ](https://qiita.com/opengl-8080/items/cb4122a19269e8e683a4#ide-%E3%81%A7%E4%BD%BF%E7%94%A8%E3%81%99%E3%82%8B)
    - IDEで使用する の欄を参照

## コーディング規約

- 基本的にService層以降もInterfaceは作成しない
    - ただし、環境別に実装を切り替えたいケースなどでは切っても良い


- フラットパッケージ構成で作成する
    - 出来るだけパッケージの階層が深くならないようにパッケージを作成すること


- Responseの作成は Service層で行う
    - 個人的にはControllerでも良いと考えているがそういう分化
    - 全体的にプレゼンテーション層のロジックをServiceに寄せる傾向がある(入力チェックとか requestパラメータを別modelに変換するとか)


- DI配下としたい共通クラスはcomponentsパッケージ配下に切る
    - ~IdCreatorのような用途が明確なクラス名にすること


- DI配下に置かない共通クラスはutilityパッケージに切る
    - 基本的にstaticメソッドのみを持つクラスを定義する
    - 出来るだけ上記のcomponentsを使用すること また、出来るだけ~Utilといった命名のクラスの定義は避けること


- クラスの型変換は~Converterや ~ Factoryという命名のクラスで行う
    - ただし、ServiceやRepositoryで直接インスタンス生成しても見通しが悪くならない場合はこの限りではない
    - Converterは変換用のクラス Factoryは材料からインスタンスを作成するという責務を負うクラス(正直使い分けはある程度適当で良い)
    - ConverterやFactoryを使用することでむしろ見通しが悪くならないかは検討すること

- 出来るだけ簡潔な命名を心がけること
    - 例えばResumeServiceクラスでレジュメを1件取得するメソッドであれば findOne といった命名にする
    - ResumeServiceというクラス名の時点でResumeを取得することは明確に理解できるので findResume のような命名は避けること

## UT

groovyのテスティングフレームワークである Spock Frameworkを使用しています。<br>
一応Junit5を実行できる環境も整えているので Junitを使用したい際は src/test/javaパッケージを作成の上<br>
上記パッケージ配下にjunitのテストクラスを作成してください。

Junitに比べて圧倒的に簡単かつ効率的にテストが書けるのでSpockの使用を推奨します。

### Spockの参考用サイト

- [Spockを始めるのに最低限必要なGroovyの基本知識](https://qiita.com/yonetty/items/4322e76f93d36ce666c2)
- [JUnit代わりにSpockを使ってみる](https://recruit.gmo.jp/engineer/jisedai/blog/junit-spock/)
- [Spockを使ってJavaのテストを効率化する](https://qiita.com/umeki_ryo/items/98336bb8badca6dc11ac)

## 参考用サイト

- [Springのレイヤーアーキテクチャ](http://terasolunaorg.github.io/guideline/public_review/Overview/ApplicationLayering.html)
- [Lombok使い方メモ](https://qiita.com/opengl-8080/items/671ffd4bf84fe5e32557)
- [Doma リファレンス](http://doma.seasar.org/reference/index.html)
- [Doma Framework](https://github.com/domaframework/doma-spring-boot)
- [spring-doc-openapi メモ書き](https://ksby.hatenablog.com/entry/2021/03/25/072126)
- [spring-doc issue #43](https://github.com/springdoc/springdoc-openapi/issues/43)
