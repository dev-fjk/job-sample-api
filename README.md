# job-sample-api

求人PJ 事前学習用API

## ブランチ運用

1. masterブランチからfeature/自身のユーザー名 という命名でブランチを作成する(例: feature/dev-fjk)
1. 上記のfeatureブランチから task/ユーザー名/機能名 という命名でブランチを作成する(例: task/dev-fjk/get-resume)

レビュー必要な場合 taskブランチからfeatureブランチへ向けてPull Requestを作成し Reviewerに dev-fjkを設定してください。

## 事前準備

以下のツールを準備してください

- JDK 11
- Groovy 3.0
- IDE(Eclipse or Intellij Ultimate Edition)
- Docker Desktop(任意)
- Maven(Eclipseの場合自前で用意必要かも・・？)

練習も兼ねてIntellij Ultimate Editionの使用を推奨します<br>
1ヶ月は無料 & JDKやmavenはIDE上からインストール可能であるため環境構築が楽です

### MySQLの準備(任意)

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

## DB定義

以下に配置しているSQLを直接参照してください

- resources/h2/init.sql(h2)
- docker/mysql/initdb.d/1_schema.sql(MySQL)


## エンドポイント

各エンドポイントの詳細IF仕様は後述のSwaggerを参照

| uri | Controller | 説明   |
|----  |----   | ---   |
| resume/v1/  | ResumeController  | 職務経歴情報の操作を行う  |
| posted-company/v1/  | PostedCompanyController  | 企業への応募関連の操作を行う  |

## API仕様書(Swagger)

- [ローカル(ビルドが必要)](http://localhost:8080/swagger-ui/index.html)

ローカルのSwagger上からAPIへリクエストを飛ばすことが可能です

## 参考用サイト

- [Springのレイヤーアーキテクチャ](http://terasolunaorg.github.io/guideline/public_review/Overview/ApplicationLayering.html)
- [Doma リファレンス](http://doma.seasar.org/reference/index.html)
- [Doma Framework](https://github.com/domaframework/doma-spring-boot)
- [spring-doc-openapi メモ書き](https://ksby.hatenablog.com/entry/2021/03/25/072126)
- [spring-doc issue #43](https://github.com/springdoc/springdoc-openapi/issues/43)
