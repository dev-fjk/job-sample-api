# miko-quiz-api
さくらみこ クイズアプリ用API

## API仕様書(Swagger UI)
- [git hub pages](https://dev-fjk.github.io/miko-quiz-api/)
- [ローカル(ビルドが必要)](http://localhost:8080/swagger-ui/index.html)

### API仕様書の更新方法
~~~
(1) ローカルでアプリケーションを起動しSwaggerのリンクを開く
(2) 検索欄に『/v3/api-docs.yaml』 と入力して検索する
(3) 左上の/v3/api-docs.yamlというリンクを押下すると api-docs.yamlがダウンロードされるので 
元々置いてある docs/specs/api-doc.yamlと差し替える

※ masterブランチのapi-docs.ymlの記載内容が反映されるので他のブランチで差し替えてもmasterにマージするまで反映されない
※ git hub actionsを入れた後はmasterマージ時に自動で最新化されるようにしたい
~~~

## 認証周りの設定
管理者用APIを叩く際の認証設定の手順

- 認証自体をOFFにする場合
    - spring.profile.activeに noauthを追加する
~~~
  -Dspring.profiles.active = local,noauth
  ※ IntelliJの場合は実行構成の 有効なプロファイル欄に記載すればOK
~~~

- 認証を行う場合([参考ページ](https://qiita.com/yushi_koga/items/6b6b4aa6fa57f771da6f))
    - ① Swagger UIからサンプルの値そのままでログインAPIを叩く(Localプロファイルのみ)
    - ② レスポンスの Authorizationヘッダのの値から　Bearer を除いたトークン文字列をコピーする
    - ③ Swagger UIのAuthorizeボタンを押下し コピーしたトークン値を貼り付けてAuthorizeボタンを押下
    - ④ 実行したいAPIを実行

## パッケージ構成
| dir1 | dir2  | dir3           | dir4       | dir5       | description
|----  |----   |----            |----        |----        | ----
| docs |       |                |            |            | git hub pagesでデプロイされるAPI仕様書
| src  |       |                |            |            |
|      | main  |                |            |            |
|      |       | application    |            |            | アプリケーション層　業務ロジック関連
|      |       |                | common     |            | 共通定義系パッケージ
|      |       |                |            | annotation | 自作アノテーションを定義
|      |       |                |            | config     | 共通的なDIのConfig定義
|      |       |                |            | constant   | 定数定義用
|      |       |                |            | utility    | 共通処理の定義用
|      |       |                | exception  |            | 自作業務例外定義用パッケージ
|      |       |                | service    |            | Serviceクラスの実装 メインのビジネスロジック実装箇所
|      |       | domain         |            |            | ドメイン層 AP層~ドメイン層で使用するmodelとIFの定義
|      |       |                | model      |            |
|      |       |                |            | consts     | Enum定義
|      |       |                |            | dto        | アプリケーション内の中間加工用のmodel定義
|      |       |                |            | result     | ドメイン層で使用する取得結果保持用のmodel定義
|      |       |                | service    |            | サービスIFの定義
|      |       |                | repository |            | リポリトリIFの定義
|      |       | infrastructure |            |            | インフラ層 外部サービスへのアクセス部
|      |       |                | repository |            | リポジトリ実装クラスの作成 daoとサービス層間のビジネスロジック盛り込み
|      |       |                | dao        |            | domaのデータアクセスリポジトリ DBにSQLを発行
|      |       |                | model      |            |
|      |       |                |            | entity     | DBから取得したデータ保持用のmodel定義
|      |       | presentation   |            |            | プレゼンテーション層 Restの送受信周りのソース定義
|      |       |                | controller |            | RestControllerの定義用パッケージ
|      |       |                | validation |            | 入力チェック用処理の定義
|      |       |                | converter  |            | IF周りの型変換を行うパッケージ
|      |       |                | model      |            |
|      |       |                |            | form       | Requestのbodyからバインドされるformのmodelクラス定義
|      |       |                |            | response   | Apiのレスポンスとして返却するmodelの定義

## 参考
- [Spring Boot 認証・認可 REST API](https://b1san-blog.com/post/spring/spring-auth/)
- [Doma リファレンス](http://doma.seasar.org/reference/index.html)
- [Doma Framework](https://github.com/domaframework/doma-spring-boot)
- [ControllerからSwaggerを自動生成](https://qiita.com/rhirabay/items/f7527c91b5defc424b9c)
- [spring-doc-openapi メモ書き](https://ksby.hatenablog.com/entry/2021/03/25/072126)
- [spring-doc issue #43](https://github.com/springdoc/springdoc-openapi/issues/43)
- [swagger ui configuration](https://swagger.io/docs/open-source-tools/swagger-ui/usage/configuration/)
- [SwaggerUIを簡単にGithub Pagesで公開する方法](https://qiita.com/youdays/items/38f15b90402d097fb13e)
- [Openapiで全APIに認証の設定（security scheme）を指定する方法](https://qiita.com/yushi_koga/items/6b6b4aa6fa57f771da6f)
