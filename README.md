# SDCardSearchMusic
Ref.
https://weselyong.pixnet.net/blog/post/46162610-%5Bandroid%5D-%E5%8F%96%E5%BE%97sd%E5%8D%A1%E5%85%A7%E6%89%80%E6%9C%89%E9%9F%B3%E6%A8%82%E8%A9%B3%E7%B4%B0%E8%B3%87%E6%96%99

This document is mainly talking about how to extract all music info in 
/data/data/com.android.providers.media/databases 
by using cursor.query. 
(Android will prescan all media files' info in SD card, and write those info to "external.db") 
--------------------- 
Android 系統會預先scan SD卡上全部的影音資料 (音樂/影片/圖片) 並且寫入到 
/data/data/com.android.providers.media/databases/external.db 
當中。 
可以想辦法抓出來用資料庫軟體打開 (例如免費的 SQLite Expert) 

裡面有許多的table 
album_art              audio                  files
album_info             audio_genres           images
albums                 audio_genres_map       search
android_metadata       audio_genres_map_noid  searchhelpertitle
artist_info            audio_meta             thumbnails
artists                audio_playlists        video
artists_albums_map     audio_playlists_map    videothumbnails

這些table都可以在Android當中用resolver.query抓出來 

在使用resolver.query抓Android媒體資料的時候的時候, 主要要下的指令: 
Cursor c = resolver.query(uri, projection, selection, selectionArgs, sortOrder) 

其中uri是必要的，依照使用需求來下 
(類似指定要看哪個table) 

* 抓音樂-音樂資料庫 (EXTERNAL代表SD卡) 
MediaStore.Audio.Media.EXTERNAL_CONTENT_URI 

* 抓音樂-專輯資料庫 
MediaStore.Audio.Album.EXTERNAL_CONTENT_URI 

* 抓所有多媒體檔案資料庫 
MediaStore.Files.FileColumns.DATA 

還有其他諸如此類的 

selection 的話就是平常SQL語句 SELECT 後面那段字串 

所以如果我今天要從全部音樂檔案裡面抓出Eminem的歌的話 
我就這樣下: 

<Cursor mediaCursor = 
resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
, null
, "ARTIST=Eminem"        // SELECT "condition string"
, null, null);

抓出來之後會得到一個對應的Cursor，指到符合條件的項目。 
然後利用 cursor.moveToFirst(), moveToLast(), moveToNext(), moveTo(int i) 
等等的指令來控制它的位置，然後再寫明要取出哪個column的資料: 
int index = c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE); 
再使用 getString(index)得到目標字串。 
最後不要用的時候 
c.close(); 


步驟如下:
// cursor 移動到第一個
c.moveToFirst();

// 取得column index
int index = c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);

// 取得內容字串
String src = c.getString(index);

// 結束的時候記得close
c.close();
