# SQLite3 コマンド

### -- SQLite3データベースにコンソールログイン

    sqlite3 lib\db\sqlite3\database.sqlite3

### -- 外部キーを有効にする
    PRAGMA foreign_keys=true;

### -- テーブルの一覧を表示する
    .table

### -- テーブルのスキーマを表示する
    .schema [table]
