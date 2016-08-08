## SIToolkit Archetype for Java EE 7 Web Application

sit-ad-archetype-javaee7-webは、Java EE 7 WebアプリケーションのMavenアーキタイプです。ミドルウェアのインストール、セットアップが自動で行えるため、すぐに開発を始めることができます。

```
mvn archetype:generate -Dfilter=:javaee7-web-min

cd myproject
mvn verify -P mysql,db-migrate,was-liberty,it
```

詳細な説明は[Wiki](../../wiki)を参照してください。使い方は[クイックスタート](../../wiki/%E3%82%AF%E3%82%A4%E3%83%83%E3%82%AF%E3%82%B9%E3%82%BF%E3%83%BC%E3%83%88)を参照してください。

## ライセンス

sit-ad-archetype-javaee7-web、及びSIToolkitのすべてのモジュールは[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)で提供しています。
