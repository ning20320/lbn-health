package lbn.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

public class QiniuUtils {
    public static String accessKey = "FDkQzI7tXgiTMDf1Kx5CY-wpvgjwEpmHTrjGoRG4";
    public static String secretKey = "MwEcV1qXyJhGAETeTeqowJIu906IKrnVz0Cg9gG4";
    public static String bucket = "zksx-20230728";

    /**
     * 文件上传
     */
    public static void upload2Qiniu(String filePath,String fileName){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(filePath, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException e) {
            Response r = e.response;
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex) {

            }
        }
    }

    /**
     * 字节数组上传
     * 可以支持将内存中的字节数组上传到空间中
     */
    public static void upload2Qiniu(byte[] bytes,String fileName){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        //其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException e) {
            Response r = e.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex) {

            }
        }
    }

    /**
     * 删除文件
     */
    public static void deleteFile(String fileName){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket,key);
        } catch (QiniuException e) {
            //如果遇到异常，说明删除失败
            System.err.println(e.code());
            System.err.println(e.response.toString());
        }
    }
}
