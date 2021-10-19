package com.kalevet.pixaget.utill.jsonHandlers

import com.kalevet.pixaget.data.models.image.ImageItem
import com.kalevet.pixaget.data.models.video.VideoItem
import com.kalevet.pixaget.data.models.video.VideoResolution
import com.kalevet.pixaget.data.models.video.VideoResolutionDetails
import com.kalevet.pixaget.data.repositories.remote.responses.ImageSearchResult
import com.kalevet.pixaget.data.repositories.remote.responses.VideoSearchResult
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.io.StringReader
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsonConverterTest {

    private val imageItemJsonString = "{\"id\":591576,\"pageURL\":\"https://pixabay.com/photos/woman-girl-freedom-happy-sun-591576/\",\"type\":\"photo\",\"tags\":\"woman, girl, freedom\",\"previewURL\":\"https://cdn.pixabay.com/photo/2015/01/07/15/51/woman-591576_150.jpg\",\"previewWidth\":150,\"previewHeight\":99,\"webformatURL\":\"https://pixabay.com/get/gf5915644f0ff62d78d91e51ec076f7906ea4cc4ec34a2e222328a233b35fcf3326e46683c2cfecd2cb4aeeadf89d54d8_640.jpg\",\"webformatWidth\":640,\"webformatHeight\":426,\"largeImageURL\":\"https://pixabay.com/get/gbb6146c787ef0677899fc10c55a2d921c19a533549d37fc7c82d91ccdc0cda9a23de1e6b7d83f516ccf89ebecc8530194b3db730dd575e6e395670b1792a727e_1280.jpg\",\"imageWidth\":4047,\"imageHeight\":2698,\"imageSize\":1555695,\"views\":1332244,\"downloads\":516095,\"collections\":6845,\"likes\":2933,\"comments\":674,\"user_id\":334088,\"user\":\"JillWellington\",\"userImageURL\":\"https://cdn.pixabay.com/user/2018/06/27/01-23-02-27_250x250.jpg\",\"id_hash\":\"591576\",\"fullHDURL\":\"https://pixabay.com/get/g172b1f437b8c8de76537f5f3caafeab6de9e0c35cfc21c099e5e04d9c59a0684fa1f5e97547b0bad83bdf9c55322b7d39cd4c8011694031409254ad39223e42c_1920.jpg\",\"imageURL\":\"https://pixabay.com/get/g4f93efc6d2bb19ae74656def966fb0878589efe772483425c73677888ca4f38bf5dd0c9ecaa66d1ded0653d74974d36a.jpg\"}"
    private val imageSearchResultJsonString = "{\"total\":55942,\"totalHits\":500,\"hits\":[{\"id\":591576,\"pageURL\":\"https://pixabay.com/photos/woman-girl-freedom-happy-sun-591576/\",\"type\":\"photo\",\"tags\":\"woman, girl, freedom\",\"previewURL\":\"https://cdn.pixabay.com/photo/2015/01/07/15/51/woman-591576_150.jpg\",\"previewWidth\":150,\"previewHeight\":99,\"webformatURL\":\"https://pixabay.com/get/gf5915644f0ff62d78d91e51ec076f7906ea4cc4ec34a2e222328a233b35fcf3326e46683c2cfecd2cb4aeeadf89d54d8_640.jpg\",\"webformatWidth\":640,\"webformatHeight\":426,\"largeImageURL\":\"https://pixabay.com/get/gbb6146c787ef0677899fc10c55a2d921c19a533549d37fc7c82d91ccdc0cda9a23de1e6b7d83f516ccf89ebecc8530194b3db730dd575e6e395670b1792a727e_1280.jpg\",\"imageWidth\":4047,\"imageHeight\":2698,\"imageSize\":1555695,\"views\":1332244,\"downloads\":516095,\"collections\":6845,\"likes\":2933,\"comments\":674,\"user_id\":334088,\"user\":\"JillWellington\",\"userImageURL\":\"https://cdn.pixabay.com/user/2018/06/27/01-23-02-27_250x250.jpg\",\"id_hash\":\"591576\",\"fullHDURL\":\"https://pixabay.com/get/g172b1f437b8c8de76537f5f3caafeab6de9e0c35cfc21c099e5e04d9c59a0684fa1f5e97547b0bad83bdf9c55322b7d39cd4c8011694031409254ad39223e42c_1920.jpg\",\"imageURL\":\"https://pixabay.com/get/g4f93efc6d2bb19ae74656def966fb0878589efe772483425c73677888ca4f38bf5dd0c9ecaa66d1ded0653d74974d36a.jpg\"},{\"id\":677774,\"pageURL\":\"https://pixabay.com/photos/sparkler-holding-hands-firework-677774/\",\"type\":\"photo\",\"tags\":\"sparkler, holding, hands\",\"previewURL\":\"https://cdn.pixabay.com/photo/2015/03/17/14/05/sparkler-677774_150.jpg\",\"previewWidth\":150,\"previewHeight\":99,\"webformatURL\":\"https://pixabay.com/get/gc86af3fab252de19a58ff69f68826c0f4b5b17eec3c6f57f2e209b71e2a20561b2832a9c4d3f3178cb86891ee2536b86_640.jpg\",\"webformatWidth\":640,\"webformatHeight\":426,\"largeImageURL\":\"https://pixabay.com/get/g47ecaa2f7b0352b17bb9eaacf5feafd08f4c1ca5689d427b0ded1db932a8a0bb00af160220e7bb8050def22e97b2bcc45a7e4398141cb5aed5afe63a825fb9e7_1280.jpg\",\"imageWidth\":2509,\"imageHeight\":1673,\"imageSize\":517213,\"views\":1883170,\"downloads\":941101,\"collections\":5842,\"likes\":2845,\"comments\":422,\"user_id\":242387,\"user\":\"Free-Photos\",\"userImageURL\":\"https://cdn.pixabay.com/user/2014/05/07/00-10-34-2_250x250.jpg\",\"id_hash\":\"677774\",\"fullHDURL\":\"https://pixabay.com/get/g55416e393bab58b5043a9715e61972255bbb70a1fcbf0fd2ce1e277c42ea6054df73280c4511416377a2c9c919c1d98ecbd511fee1af3c34a4ee78e6fca1b227_1920.jpg\",\"imageURL\":\"https://pixabay.com/get/gc85f0435a62b8ebcd37318fcae2ba8487730b34d9338b3ee46536680ad693635298c8e99652c701d9cffc942f983e72c.jpg\"},{\"id\":2696947,\"pageURL\":\"https://pixabay.com/photos/girl-face-colorful-colors-artistic-2696947/\",\"type\":\"photo\",\"tags\":\"girl, face, colorful\",\"previewURL\":\"https://cdn.pixabay.com/photo/2017/08/30/12/45/girl-2696947_150.jpg\",\"previewWidth\":150,\"previewHeight\":150,\"webformatURL\":\"https://pixabay.com/get/gf21a262830eee9acbae3f5c6e8657fcc4fb46952d462f094a28c10d459d7a016b337b6df747e1b2221268020d8ebb7c0c12285701f90d30f9c5566283f6db01d_640.jpg\",\"webformatWidth\":640,\"webformatHeight\":640,\"largeImageURL\":\"https://pixabay.com/get/gdc60df45aa5854318d492b6a5e7aed479bca8f7cf949b0414bdacabdd6e918c57874ef40f633fdc5410be1cab1ade7db4187a2411b3b095105cd95e11f430d24_1280.jpg\",\"imageWidth\":2276,\"imageHeight\":2276,\"imageSize\":3548285,\"views\":1398991,\"downloads\":1053777,\"collections\":5300,\"likes\":2798,\"comments\":379,\"user_id\":1982503,\"user\":\"ivanovgood\",\"userImageURL\":\"https://cdn.pixabay.com/user/2017/11/29/19-47-26-843_250x250.jpg\",\"id_hash\":\"2696947\",\"fullHDURL\":\"https://pixabay.com/get/gab3e76f9b6dad50cb140841c3402c71ec58608939655648a9a4ddaa510f395b1105ebb375f804ce48ae69fccdf70c071faa6b12f9337ea43190f0448eaba7ce0_1920.jpg\",\"imageURL\":\"https://pixabay.com/get/gd1282d4087073c3938df4f18a9062760fedea9f790747e54130cb5cab6a064fa9b9d8bf13c97033d1776737f69e860e8.jpg\"}]}"
    private val imageItemObject1 = ImageItem(id = 591576, pageURL = "https://pixabay.com/photos/woman-girl-freedom-happy-sun-591576/", type = "photo", tags = "woman, girl, freedom", previewURL = "https://cdn.pixabay.com/photo/2015/01/07/15/51/woman-591576_150.jpg", previewWidth = 150, previewHeight = 99, webformatURL = "https://pixabay.com/get/gf5915644f0ff62d78d91e51ec076f7906ea4cc4ec34a2e222328a233b35fcf3326e46683c2cfecd2cb4aeeadf89d54d8_640.jpg", webformatWidth = 640, webformatHeight = 426, largeImageURL = "https://pixabay.com/get/gbb6146c787ef0677899fc10c55a2d921c19a533549d37fc7c82d91ccdc0cda9a23de1e6b7d83f516ccf89ebecc8530194b3db730dd575e6e395670b1792a727e_1280.jpg", imageWidth = 4047, imageHeight = 2698, imageSize = 1555695, views = 1332244, downloads = 516095, likes = 2933, comments = 674, user_id = 334088, user = "JillWellington", userImageURL = "https://cdn.pixabay.com/user/2018/06/27/01-23-02-27_250x250.jpg", fullHDURL = "https://pixabay.com/get/g172b1f437b8c8de76537f5f3caafeab6de9e0c35cfc21c099e5e04d9c59a0684fa1f5e97547b0bad83bdf9c55322b7d39cd4c8011694031409254ad39223e42c_1920.jpg", imageURL = "https://pixabay.com/get/g4f93efc6d2bb19ae74656def966fb0878589efe772483425c73677888ca4f38bf5dd0c9ecaa66d1ded0653d74974d36a.jpg", vectorURL = null)
    private val imageItemObject2 = ImageItem(id = 677774, pageURL = "https://pixabay.com/photos/sparkler-holding-hands-firework-677774/", type = "photo", tags = "sparkler, holding, hands", previewURL = "https://cdn.pixabay.com/photo/2015/03/17/14/05/sparkler-677774_150.jpg", previewWidth = 150, previewHeight = 99, webformatURL = "https://pixabay.com/get/gc86af3fab252de19a58ff69f68826c0f4b5b17eec3c6f57f2e209b71e2a20561b2832a9c4d3f3178cb86891ee2536b86_640.jpg", webformatWidth = 640, webformatHeight = 426, largeImageURL = "https://pixabay.com/get/g47ecaa2f7b0352b17bb9eaacf5feafd08f4c1ca5689d427b0ded1db932a8a0bb00af160220e7bb8050def22e97b2bcc45a7e4398141cb5aed5afe63a825fb9e7_1280.jpg", imageWidth = 2509, imageHeight = 1673, imageSize = 517213, views = 1883170, downloads = 941101, likes = 2845, comments = 422, user_id = 242387, user = "Free-Photos", userImageURL = "https://cdn.pixabay.com/user/2014/05/07/00-10-34-2_250x250.jpg", fullHDURL = "https://pixabay.com/get/g55416e393bab58b5043a9715e61972255bbb70a1fcbf0fd2ce1e277c42ea6054df73280c4511416377a2c9c919c1d98ecbd511fee1af3c34a4ee78e6fca1b227_1920.jpg", imageURL = "https://pixabay.com/get/gc85f0435a62b8ebcd37318fcae2ba8487730b34d9338b3ee46536680ad693635298c8e99652c701d9cffc942f983e72c.jpg", vectorURL = null)
    private val imageItemObject3 = ImageItem(id = 2696947, pageURL = "https://pixabay.com/photos/girl-face-colorful-colors-artistic-2696947/", type = "photo", tags = "girl, face, colorful", previewURL = "https://cdn.pixabay.com/photo/2017/08/30/12/45/girl-2696947_150.jpg", previewWidth = 150, previewHeight = 150, webformatURL = "https://pixabay.com/get/gf21a262830eee9acbae3f5c6e8657fcc4fb46952d462f094a28c10d459d7a016b337b6df747e1b2221268020d8ebb7c0c12285701f90d30f9c5566283f6db01d_640.jpg", webformatWidth = 640, webformatHeight = 640, largeImageURL = "https://pixabay.com/get/gdc60df45aa5854318d492b6a5e7aed479bca8f7cf949b0414bdacabdd6e918c57874ef40f633fdc5410be1cab1ade7db4187a2411b3b095105cd95e11f430d24_1280.jpg", imageWidth = 2276, imageHeight = 2276, imageSize = 3548285, views = 1398991, downloads = 1053777, likes = 2798, comments = 379, user_id = 1982503, user = "ivanovgood", userImageURL = "https://cdn.pixabay.com/user/2017/11/29/19-47-26-843_250x250.jpg", fullHDURL = "https://pixabay.com/get/gab3e76f9b6dad50cb140841c3402c71ec58608939655648a9a4ddaa510f395b1105ebb375f804ce48ae69fccdf70c071faa6b12f9337ea43190f0448eaba7ce0_1920.jpg", imageURL = "https://pixabay.com/get/gd1282d4087073c3938df4f18a9062760fedea9f790747e54130cb5cab6a064fa9b9d8bf13c97033d1776737f69e860e8.jpg", vectorURL = null)
    private val imageSearchResultObject = ImageSearchResult(total = 55942, totalHits = 500, hits = arrayListOf(imageItemObject1, imageItemObject2, imageItemObject3))
    private val videoItemJsonString = "{\"id\":445,\"pageURL\":\"https://pixabay.com/videos/id-445/\",\"type\":\"film\",\"tags\":\"yoga, health, exercise\",\"duration\":14,\"picture_id\":\"530631990-09af7f4ef05e9c3274483409394e9363d62ba414dce2cfeec145e1a94e25464d-d\",\"videos\":{\"large\":{\"url\":\"\",\"width\":0,\"height\":0,\"size\":0},\"medium\":{\"url\":\"https://player.vimeo.com/external/136216234.hd.mp4?s=fe9ef092c95053276a5fbe82a2a80cad04ce4943&profile_id=113\",\"width\":1280,\"height\":720,\"size\":1830180},\"small\":{\"url\":\"https://player.vimeo.com/external/136216234.sd.mp4?s=52b4975c2a6cbcdbcaf624536e9f7595afcfeee2&profile_id=112\",\"width\":640,\"height\":360,\"size\":500652},\"tiny\":{\"url\":\"https://player.vimeo.com/external/136216234.mobile.mp4?s=9f3b9f4a6bb85c51c1bad519f57fc7fee612156c&profile_id=116\",\"width\":480,\"height\":270,\"size\":425567}},\"views\":223040,\"downloads\":98241,\"likes\":414,\"comments\":123,\"user_id\":1283884,\"user\":\"Vimeo-Free-Videos\",\"userImageURL\":\"https://cdn.pixabay.com/user/2015/08/09/12-33-44-788_250x250.png\"}"
    private val videoResolutionDetails11 = VideoResolutionDetails(url = "", width = 0, height = 0, size = 0)
    private val videoResolutionDetails12 = VideoResolutionDetails(url = "https://player.vimeo.com/external/136216234.hd.mp4?s=fe9ef092c95053276a5fbe82a2a80cad04ce4943&profile_id=113", width = 1280, height = 720, size = 1830180)
    private val videoResolutionDetails13 = VideoResolutionDetails(url = "https://player.vimeo.com/external/136216234.sd.mp4?s=52b4975c2a6cbcdbcaf624536e9f7595afcfeee2&profile_id=112", width = 640, height = 360, size = 500652)
    private val videoResolutionDetails14 = VideoResolutionDetails(url = "https://player.vimeo.com/external/136216234.mobile.mp4?s=9f3b9f4a6bb85c51c1bad519f57fc7fee612156c&profile_id=116", width = 480, height = 270, size = 425567)
    private val videoResolution1 = VideoResolution(large = videoResolutionDetails11, medium = videoResolutionDetails12, small = videoResolutionDetails13, tiny = videoResolutionDetails14)
    private val videoItemObject1 = VideoItem(id = 445, pageURL = "https://pixabay.com/videos/id-445/", type = "film", tags = "yoga, health, exercise", duration = 14, picture_id = "530631990-09af7f4ef05e9c3274483409394e9363d62ba414dce2cfeec145e1a94e25464d-d", videos = videoResolution1, views = 223040, downloads = 98241, likes = 414, comments = 123, user_id = 1283884, user = "Vimeo-Free-Videos", userImageURL = "https://cdn.pixabay.com/user/2015/08/09/12-33-44-788_250x250.png", pictureURL = null)
    private val videoResolutionDetails21 = VideoResolutionDetails(url = "https://player.vimeo.com/external/436237650.hd.mp4?s=3a0d4b599aefb833717074692bce80ff37c82c3e&profile_id=175", width = 1920, height = 1080, size = 11917983)
    private val videoResolutionDetails22 = VideoResolutionDetails(url = "https://player.vimeo.com/external/436237650.hd.mp4?s=3a0d4b599aefb833717074692bce80ff37c82c3e&profile_id=174", width = 1280, height = 720, size = 5877678)
    private val videoResolutionDetails23 = VideoResolutionDetails(url = "https://player.vimeo.com/external/436237650.sd.mp4?s=86c7cae7cb144ac04e8f38eea6157b480d72ef1b&profile_id=165", width = 960, height = 540, size = 3451923)
    private val videoResolutionDetails24 = VideoResolutionDetails(url = "https://player.vimeo.com/external/436237650.sd.mp4?s=86c7cae7cb144ac04e8f38eea6157b480d72ef1b&profile_id=164", width = 640, height = 360, size = 1202776)
    private val videoResolution2 = VideoResolution(large = videoResolutionDetails21, medium = videoResolutionDetails22, small = videoResolutionDetails23, tiny = videoResolutionDetails24)
    private val videoItemObject2 = VideoItem(id = 43633, pageURL = "https://pixabay.com/videos/id-43633/", type = "film", tags = "hair, wind, girl", duration = 17, picture_id = "920851512-9043e950fdf18079fcb4f9831b4d5fc3454bf605d0b6b6a3a7dd7d0100601f7c-d", videos = videoResolution2, views = 146816, downloads = 47815, likes = 264, comments = 90, user_id = 3867243, user = "Pixource", userImageURL = "", pictureURL = null)
    private val videoResolutionDetails31 = VideoResolutionDetails(url = "https://player.vimeo.com/external/135735330.hd.mp4?s=012347da5b0b3fdad2a0c80cd2d1d45fc74d606f&profile_id=119", width = 1920, height = 1080, size = 8253846)
    private val videoResolutionDetails32 = VideoResolutionDetails(url = "https://player.vimeo.com/external/135735330.hd.mp4?s=012347da5b0b3fdad2a0c80cd2d1d45fc74d606f&profile_id=113", width = 1280, height = 720, size = 4344186)
    private val videoResolutionDetails33 = VideoResolutionDetails(url = "https://player.vimeo.com/external/135735330.sd.mp4?s=74b6673d22a462fd142720eebfd4a722c6415d1f&profile_id=112", width = 640, height = 360, size = 1234999)
    private val videoResolutionDetails34 = VideoResolutionDetails(url = "https://player.vimeo.com/external/135735330.mobile.mp4?s=6eca7f0616dac51cbb2004b11f3b08e121e42007&profile_id=116", width = 480, height = 270, size = 510428)
    private val videoResolution3 = VideoResolution(large = videoResolutionDetails31, medium = videoResolutionDetails32, small = videoResolutionDetails33, tiny = videoResolutionDetails34)
    private val videoItemObject3 = VideoItem(id = 91, pageURL = "https://pixabay.com/videos/id-91/", type = "film", tags = "photographer, beach, photography", duration = 15, picture_id = "529925632-30eb5d39cebd238bea545aacfacc57985298c1c383eb1f2b6e518d64f8a9a589-d", videos = videoResolution3, views = 262908, downloads = 119862, likes = 440, comments = 152, user_id = 1281706, user = "Coverr-Free-Footage", userImageURL = "https://cdn.pixabay.com/user/2015/10/16/09-28-45-303_250x250.png", pictureURL = null)
    private val videoSearchResultObject = VideoSearchResult(total = 484, totalHits = 484, hits = arrayListOf(videoItemObject1, videoItemObject2, videoItemObject3))
    private val videoSearchResultJsonString = "{\"total\":484,\"totalHits\":484,\"hits\":[{\"id\":445,\"pageURL\":\"https://pixabay.com/videos/id-445/\",\"type\":\"film\",\"tags\":\"yoga, health, exercise\",\"duration\":14,\"picture_id\":\"530631990-09af7f4ef05e9c3274483409394e9363d62ba414dce2cfeec145e1a94e25464d-d\",\"videos\":{\"large\":{\"url\":\"\",\"width\":0,\"height\":0,\"size\":0},\"medium\":{\"url\":\"https://player.vimeo.com/external/136216234.hd.mp4?s=fe9ef092c95053276a5fbe82a2a80cad04ce4943&profile_id=113\",\"width\":1280,\"height\":720,\"size\":1830180},\"small\":{\"url\":\"https://player.vimeo.com/external/136216234.sd.mp4?s=52b4975c2a6cbcdbcaf624536e9f7595afcfeee2&profile_id=112\",\"width\":640,\"height\":360,\"size\":500652},\"tiny\":{\"url\":\"https://player.vimeo.com/external/136216234.mobile.mp4?s=9f3b9f4a6bb85c51c1bad519f57fc7fee612156c&profile_id=116\",\"width\":480,\"height\":270,\"size\":425567}},\"views\":223040,\"downloads\":98241,\"likes\":414,\"comments\":123,\"user_id\":1283884,\"user\":\"Vimeo-Free-Videos\",\"userImageURL\":\"https://cdn.pixabay.com/user/2015/08/09/12-33-44-788_250x250.png\"},{\"id\":43633,\"pageURL\":\"https://pixabay.com/videos/id-43633/\",\"type\":\"film\",\"tags\":\"hair, wind, girl\",\"duration\":17,\"picture_id\":\"920851512-9043e950fdf18079fcb4f9831b4d5fc3454bf605d0b6b6a3a7dd7d0100601f7c-d\",\"videos\":{\"large\":{\"url\":\"https://player.vimeo.com/external/436237650.hd.mp4?s=3a0d4b599aefb833717074692bce80ff37c82c3e&profile_id=175\",\"width\":1920,\"height\":1080,\"size\":11917983},\"medium\":{\"url\":\"https://player.vimeo.com/external/436237650.hd.mp4?s=3a0d4b599aefb833717074692bce80ff37c82c3e&profile_id=174\",\"width\":1280,\"height\":720,\"size\":5877678},\"small\":{\"url\":\"https://player.vimeo.com/external/436237650.sd.mp4?s=86c7cae7cb144ac04e8f38eea6157b480d72ef1b&profile_id=165\",\"width\":960,\"height\":540,\"size\":3451923},\"tiny\":{\"url\":\"https://player.vimeo.com/external/436237650.sd.mp4?s=86c7cae7cb144ac04e8f38eea6157b480d72ef1b&profile_id=164\",\"width\":640,\"height\":360,\"size\":1202776}},\"views\":146816,\"downloads\":47815,\"likes\":264,\"comments\":90,\"user_id\":3867243,\"user\":\"Pixource\",\"userImageURL\":\"\"},{\"id\":91,\"pageURL\":\"https://pixabay.com/videos/id-91/\",\"type\":\"film\",\"tags\":\"photographer, beach, photography\",\"duration\":15,\"picture_id\":\"529925632-30eb5d39cebd238bea545aacfacc57985298c1c383eb1f2b6e518d64f8a9a589-d\",\"videos\":{\"large\":{\"url\":\"https://player.vimeo.com/external/135735330.hd.mp4?s=012347da5b0b3fdad2a0c80cd2d1d45fc74d606f&profile_id=119\",\"width\":1920,\"height\":1080,\"size\":8253846},\"medium\":{\"url\":\"https://player.vimeo.com/external/135735330.hd.mp4?s=012347da5b0b3fdad2a0c80cd2d1d45fc74d606f&profile_id=113\",\"width\":1280,\"height\":720,\"size\":4344186},\"small\":{\"url\":\"https://player.vimeo.com/external/135735330.sd.mp4?s=74b6673d22a462fd142720eebfd4a722c6415d1f&profile_id=112\",\"width\":640,\"height\":360,\"size\":1234999},\"tiny\":{\"url\":\"https://player.vimeo.com/external/135735330.mobile.mp4?s=6eca7f0616dac51cbb2004b11f3b08e121e42007&profile_id=116\",\"width\":480,\"height\":270,\"size\":510428}},\"views\":262908,\"downloads\":119862,\"likes\":440,\"comments\":152,\"user_id\":1281706,\"user\":\"Coverr-Free-Footage\",\"userImageURL\":\"https://cdn.pixabay.com/user/2015/10/16/09-28-45-303_250x250.png\"}]}"

    private val moshiAdapter = MoshiAdapter()
    private val jacksonAdapter = JacksonAdapter()
    private val gsonAdapter = GsonAdapter()


    @Suppress("unused")
    fun jsonConverterProvider(): Stream<JsonConverter> {
        return Stream.of(moshiAdapter,jacksonAdapter,gsonAdapter)
    }


    @ParameterizedTest
    @MethodSource("jsonConverterProvider")
    fun `converting jsonString to ImageItem expecting equals`(jsonConverter: JsonConverter) {
        val reader = StringReader(imageItemJsonString)
        val imageItemFromAdapter = jsonConverter.convert(reader, ImageItem::class.java)
        Assertions.assertEquals(imageItemObject1, imageItemFromAdapter)
    }

    @ParameterizedTest
    @MethodSource("jsonConverterProvider")
    fun `converting jsonString to ImageItem expecting not equals`(jsonConverter: JsonConverter) {
        val imageItemObject = imageItemObject1.copy(id=1)
        val reader = StringReader(imageItemJsonString)
        val imageItemFromAdapter = jsonConverter.convert(reader, ImageItem::class.java)
        Assertions.assertNotEquals(imageItemObject, imageItemFromAdapter)
    }

    @ParameterizedTest
    @MethodSource("jsonConverterProvider")
    fun `converting jsonString to ImageSearchResult expecting equals`(jsonConverter: JsonConverter) {
        val reader = StringReader(imageSearchResultJsonString)
        val imageSearchResultFromAdapter = jsonConverter.convert(reader, ImageSearchResult::class.java)
        Assertions.assertEquals(imageSearchResultObject, imageSearchResultFromAdapter)
    }

    @ParameterizedTest
    @MethodSource("jsonConverterProvider")
    fun `converting jsonString to ImageSearchResult expecting not equals`(jsonConverter: JsonConverter) {
        val imageSearchResult = imageSearchResultObject.copy(total = 1)
        val reader = StringReader(imageSearchResultJsonString)
        val imageSearchResultFromAdapter = jsonConverter.convert(reader, ImageSearchResult::class.java)
        Assertions.assertNotEquals(imageSearchResult, imageSearchResultFromAdapter)
    }

    @ParameterizedTest
    @MethodSource("jsonConverterProvider")
    fun `converting jsonString to VideoItem expecting equals`(jsonConverter: JsonConverter) {
        val reader = StringReader(videoItemJsonString)
        val videoItemFromAdapter = jsonConverter.convert(reader, VideoItem::class.java)
        Assertions.assertEquals(videoItemObject1, videoItemFromAdapter)
    }

    @ParameterizedTest
    @MethodSource("jsonConverterProvider")
    fun `converting jsonString to VideoItem expecting not equals`(jsonConverter: JsonConverter) {
        val videoItemObject = videoItemObject1.copy(id=1)
        val reader = StringReader(videoItemJsonString)
        val videoItemFromAdapter = jsonConverter.convert(reader, VideoItem::class.java)
        Assertions.assertNotEquals(videoItemObject, videoItemFromAdapter)
    }

    @ParameterizedTest
    @MethodSource("jsonConverterProvider")
    fun `converting jsonString to VideoSearchResult expecting equals`(jsonConverter: JsonConverter) {
        val reader = StringReader(videoSearchResultJsonString)
        val videoSearchResultFromAdapter = jsonConverter.convert(reader, VideoSearchResult::class.java)
        Assertions.assertEquals(videoSearchResultObject, videoSearchResultFromAdapter)
    }

    @ParameterizedTest
    @MethodSource("jsonConverterProvider")
    fun `converting jsonString to VideoSearchResult expecting not equals`(jsonConverter: JsonConverter) {
        val videoSearchResult = videoSearchResultObject.copy(total = 1)
        val reader = StringReader(videoSearchResultJsonString)
        val videoSearchResultFromAdapter = jsonConverter.convert(reader, VideoSearchResult::class.java)
        Assertions.assertNotEquals(videoSearchResult, videoSearchResultFromAdapter)
    }
}