# job-sample-api

求人PJ 事前学習用API

## エンドポイント

各エンドポイントの詳細IF仕様は後述のSwaggerを参照

| uri | Controller | 説明   |
|----  |----   | ---   |
| resume/v1/  | ResumeController  | 職務経歴情報の操作を行う  |
| posted-company/v1/  | PostedCompanyController  | 企業への応募関連の操作を行う  |

## API仕様書(Swagger)

- [git hub pages](https://y-gw-study.github.io/job-sample-api/)
- [ローカル(ビルドが必要)](http://localhost:8080/swagger-ui/index.html)

ローカルのSwagger上からAPIへリクエストを飛ばすことが可能です<br>
※git hub pages上だと 日付系のパラメータが上手く表示されない事象あり

### API仕様書の更新方法

~~~
(1) ローカルでアプリケーションを起動しSwaggerのリンクを開く
(2) 検索欄に『/v3/api-docs.yaml』 と入力して検索する
(3) 左上の/v3/api-docs.yamlというリンクを押下すると api-docs.yamlがダウンロードされるので 
元々置いてある docs/specs/api-doc.yamlと差し替える

※ masterブランチのapi-docs.ymlの記載内容が反映されるので他のブランチで差し替えてもmasterにマージするまで反映されない
~~~

## DB定義
- [DB設計](https://namugahaku.atlassian.net/l/cp/DA8wbXq6)

※confluenceのアカウントが必要かも

DDLは以下
- resources/h2/init.sql(h2)
- docker/mysql/initdb.d/1_schema.sql(MySQL)

## ブランチ運用

1. masterブランチからfeature/自身のユーザー名 という命名でブランチを作成する(例: feature/dev-fjk)
1. 上記のfeatureブランチから task/ユーザー名/機能名 という命名でブランチを作成する(例: task/dev-fjk/get-resume)
1. taskブランチで開発を進める。切りのいい段階で featureに向けてPull Requestを作成する

レビューが必要な場合 Reviewerに dev-fjkを設定してください。

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

spring.profiles.activeの値を使用したいDBに合わせて設定

- MySQL使用時
    - localプロファイル(デフォルト)
- h2使用時
    - h2プロファイル

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
    - 基本的には上記のcomponentsを使用すること また、出来るだけ~Utilといった命名のクラスの定義は避けること


- クラスの型変換は~Converterや ~ Factoryという命名のクラスで行う
    - ただし、ServiceやRepositoryで直接インスタンス生成しても見通しが悪くならない場合はこの限りではない
    - Converterは変換用のクラス Factoryは材料からインスタンスを作成するという責務を負うクラス(正直使い分けはある程度適当で良い)


- 出来るだけ簡潔な命名を心がけること
    - 例えばResumeServiceクラスでレジュメを1件取得するメソッドであれば findOne といった命名にする
    - ResumeServiceというクラス名の時点でResumeを取得することは明確に理解できるので findResume のような命名は避けること

### プラグイン設定

OR Mapperとして Searar Domaを使用するため以下のプラグインを導入することを推奨します。

- Eclipse
    - http://doma.seasar.org/extension/doma_tools.html
- IntelliJ
    - https://plugins.jetbrains.com/plugin/7615-doma-support

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
- [Doma リファレンス](http://doma.seasar.org/reference/index.html)
- [Doma Framework](https://github.com/domaframework/doma-spring-boot)
- [spring-doc-openapi メモ書き](https://ksby.hatenablog.com/entry/2021/03/25/072126)
- [spring-doc issue #43](https://github.com/springdoc/springdoc-openapi/issues/43)
