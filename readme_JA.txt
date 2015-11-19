--------------------------------
【バージョン】
--------------------------------
Ver.5.1.0

■変更履歴
-----------
2014/6/13:
- Version5.1に対応しました。

2013/12/15:
- Version5.0に対応しました。

2013/08/28:
- Version4.2追加API：AdGroupBidMultiplierServiceに対応しました。
- Soap APIライブラリをJAX-WSに変更しました。

2013/07/22:
- Version4.2に対応しました。V4.0からの変更点は以下になります。
-- AdDisplayOptionSample.javaの追加

--------------------------------
【概要】
--------------------------------
このサンプルプログラムは、Javaを使用して各APIを呼び出す処理サンプルです。
JAX-WSを使用してAPIを呼び出す形になっています。

--------------------------------
【内容物】
--------------------------------
■binディレクトリ
コンパイルされたサンプルプログラムの実行ファイルと、
Windows上で実行するためのバッチファイルが格納されています。

- run_sample.bat：Windows上でサンプルプログラムを実行するためのバッチファイルです。
- sample.jar    ：コンパイルされたサンプルプログラムがまとめたjarファイルです。



■confディレクトリ
サンプルプログラム実行時の各種設定を記述するプロパティファイルが格納されています。

- api_config.properties：各種IDを記述する設定ファイルです。


■srcディレクトリ
以下の各プログラムが格納されています。

・以下は直接実行できるサンプルプログラムです。

- accountSample/AccountSample.java                     :AccountServiceによるアカウント参照、更新処理のサンプルです。
- adDisplayOptionSample/AdDisplayOptionSample.java     :FeedItemService/CampaignFeedService/AdGroupFeedServiceによる広告表示オプションの登録、参照、更新処理のサンプルです。
- adSample/AdSample.java                               :BiddingStrategyService/CampaignService/CampaignTargetService/CampaignCriterionService/AdGroupService/AdGroupCriterionService/AdGroupAdService/AdGroupBidMultiplierServiceによる入稿処理のサンプルです。
- adSample/BiddingStrategyServiceSample.java           :BiddingStrategyServiceによる自動入札設定の登録、参照、更新、削除処理のサンプルです。
- adSample/CampaignServiceSample.java                  :CampaignServiceによるキャンペーンの登録、参照、更新、削除処理のサンプルです。
- adSample/CampaignTargetServiceSample.java            :CampaignTargetServiceによるキャンペーンターゲティング設定の登録、参照、更新、削除処理のサンプルです。
- adSample/CampaignCriterionServiceSample.java         :CampaignCriterionServiceによるキャンペーン除外クライテリアの登録、参照処理のサンプルです。
- adSample/AdGroupServiceSample.java                   :AdGroupServiceによる広告グループの登録、参照、更新、削除処理のサンプルです。
- adSample/AdGroupCriterionServiceSample.java          :AdGroupCriterionServiceによる広告グループクライテリアの登録、参照、更新、削除処理のサンプルです。
- adSample/AdGroupBidMultiplierServiceSample.java      :AdGroupBidMultiplierServiceによる広告グループ入札価格調整率の参照、更新処理のサンプルです。
- adSample/AdGroupAdServiceSample.java                 :AdGroupAdServiceによる広告の登録、参照、更新、削除処理のサンプルです。
- balanceSample/BalanceSample.java                     :BalanceServiceによるアカウント残高を参照する処理のサンプルです。
- bidLandscapeSample/BidLandscapeSample.java           :BidLandscapeServiceによるビットのシュミレート情報を参照する処理のサンプルです。
- bulkDownloadSample/BulkDownloadSample.java           :BulkServiceによるダウンロード処理のサンプルです。
- bulkUploadSample/BulkUploadSample.java               :BulkServiceによるアップロード処理のサンプルです。
- customerSyncSample/CustomerSyncSample.java           :CustomerSyncServiceによるアカウント、キャンペーン情報の更新履歴参照処理のサンプルです。
- conversionTrackerSample/ConversionTrackerSample.java :ConversionTrackerServiceによるコンバージョントラック情報の登録、参照、更新処理のサンプルです。
- dictionarySample/DictionarySample.java               :DictionaryServiceによる審査否認理由の参照、地域コード参照処理のサンプルです。
- keywordEstimatorSample/KeywordEstimatorSample.java   :KeywordEstimatorServiceによるキャンペーン及び広告グループのキーワードのクリック単価や掲載順位などの予測値を参照する処理のサンプルです。
- reportDownloadSample/ReportDownloadSample.java       :ReportDefinitionService, ReportServiceを使用したレポートダウンロード処理のサンプルです。
- targetingIdeaSample/TargetingIdeaSample.java         :TargetingIdeaServiceによる推奨キーワードを参照する処理のサンプルです。
- trafficEstimatorSample/TrafficEstimatorSample.java   :TrafficEstimatorServiceによる指定キーワードのクリック単価や掲載順位などの予測値を参照する処理のサンプルです。

・以下は各サンプルプログラムから利用されるクラスです。

- util/SoapUtils.java       :LocationServiceを使用したリクエスト先の取得処理のサンプル及びその他共通処理です。
- jp/yahooapis/ss/V5配下    :JAX-WSを使用してWSDLから生成したスタブ、スケルトンクラス群です。


■downloadディレクトリ
ReportDownloadSample、BulkDownloadSample、BulkUploadSampleを実行した際に、
ダウンロードしたデータがファイルとして格納されるディレクトリです。

■uploadディレクトリ
BulkUploadSampleでアップロードするファイルをあらかじめ格納しておくディレクトリです。


--------------------------------
【環境設定】
--------------------------------
Java環境を構築するために、以下をインストールしてください。

[Java (J2SE) 1.6]
http://www.oracle.com/technetwork/java/javase/downloads/index.html


confディレクトリ配下にあるapi_config.propertiesに各IDを記述します。

LOCATION            : リクエスト先毎にコメントアウトを外してください。
LICENSE             : APIライセンスを記述(必須)
APIACCOUNTID        : APIアカウントIDを記述(必須)
APIACCOUNTPASSWORD  : APIアカウントパスワードを記述(必須)
ONBEHALFOFACCOUNTID : 代行アカウントを記述(任意)
ONBEHALFOFPASSWORD  : 代行アカウントパスワードを記述(任意)
ACCOUNTID           : アカウントIDを記述(必須)

以下、IDはBidLandscapeSampleを動作させる際に必要となります。
BIDDINGSTRATEGYID   : 自動入札IDを記述（必須）
CAMPAIGNID          : キャンペーンIDを記述（必須）
ADGROUPID           : 広告グループIDを記述（必須）
ADGROUPCRITERIONIDS : 広告グループのクライテリアIDを記述（任意）
                      カンマ区切りで複数IDを指定することができます。

--------------------------------
【実行】
--------------------------------
set CLASS_PATH=%SAMPLE_HOME%\conf
set CLASS_PATH=%SAMPLE_HOME%\bin\sample.jar;%CLASS_PATH%

■例：
---------------------------------------
java -classpath %CLASS_PATH% accountSample.AccountSample
java -classpath %CLASS_PATH% adDisplayOptionSample.AdDisplayOptionSample
java -classpath %CLASS_PATH% adSample.AdSample
java -classpath %CLASS_PATH% balanceSample.BalanceSample
java -classpath %CLASS_PATH% bidLandscapeSample.BidLandscapeSample
java -classpath %CLASS_PATH% bulkDownloadSample.BulkDownloadSample
java -classpath %CLASS_PATH% bulkUploadSample.BulkUploadSample
java -classpath %CLASS_PATH% customerSyncSample.CustomerSyncSample
java -classpath %CLASS_PATH% conversionTrackerSample.ConversionTrackerSample
java -classpath %CLASS_PATH% dictionarySample.DictionarySample
java -classpath %CLASS_PATH% keywordEstimatorSample.KeywordEstimatorSample
java -classpath %CLASS_PATH% reportDownloadSample.ReportDownloadSample
java -classpath %CLASS_PATH% targetingIdeaSample.TargetingIdeaSample
java -classpath %CLASS_PATH% trafficEstimatorSample.TrafficEstimatorSample
---------------------------------------

データをダウンロードする処理を実行した場合には、
downloadディレクトリにファイルが格納されます。

データをアップロードする処理を実行する場合には
実行前にあらかじめuploadディレクトリ配下にアップロードしたい
ファイルをuploadディレクトリ配下に格納しておく必要があります。
サンプルプログラムごとにファイル名は固定です。

・BulkUploadSampleの場合：SampleBulkUpload.csv
