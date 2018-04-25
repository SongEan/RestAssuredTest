import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Before;
import org.junit.Test;

import java.sql.Array;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class SDKLoginTest {
    static int statusCode;
    public static RequestSpecification requestSpecification;
    public static ResponseSpecification responseSpecification;


    public static void tesBeforeLogin(){
        //定义通用URL,header
        RestAssured.baseURI="https://mpay.mgame.360.cn";

        //ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        responseSpecification = new ResponseSpecBuilder().build();
        requestSpecification = new RequestSpecBuilder().build();

        requestSpecification.header("User-Agent","Qhopensdk-2.1.0-652-0;default");
        requestSpecification.header("Connection","Keep-Alive");
        responseSpecification.statusCode(200);

        statusCode=given()
                .spec(requestSpecification)
                .queryParam("Q", "u%3DG.rfgre%26n%3D%25Q0%25N1%25ON%25P5%25P2%25S3%25Q1%25OS%25PP%25P7%26le%3DZGZ2AGRmAwVkZwHyAQNkAwZhL29g%26m%3DZGZ2WGWOWGWOWGWOWGWOWGWOZGV1%26qid%3D1461589169%26im%3D1_t01228f09984f1b8146%26src%3Dmpc_yxhezi_and_201696301%26t%3D1")
                .queryParam("T", "s%3Dfd8658268c0ea68bb93ecb267be0513a%26t%3D1524450754%26lm%3D%26lf%3D4%26sk%3D4185f3a4633ab22aa2579c570505160a%26mt%3D1524450754%26rc%3D%26v%3D2.0%26a%3D1")
                .when()
                .log().all()
                //直接调用定义的通用url域名
                .post("/mobile/user_config_v3")
                .then()
                .spec(responseSpecification)
                .log().all()
                .statusCode(200).extract().statusCode();
        System.out.println("获取statusCode= "+statusCode);
    }

    @Before
    public void login(){
        //useRelaxedHTTPSValidation();
        //常用方法定义封装一个方法
        tesBeforeLogin();
    }


    @Test
    public void testLogin() {

        given()
                .queryParam("m1","f002da3da51f516d6a91f69d4ce1b39c")
                .queryParam("os","8.0.0")
                .queryParam("m2","132f34372b0b860640a87847d29ab59d")
                .queryParam("nt","1")
                .queryParam("pname","com.qihoo.gamecenter.sdk.demosp")
                .queryParam("pluginid","1")
                .queryParam("pluginversion","2102")
                .queryParam("channel","default")
                .queryParam("sdkver","652")
                .queryParam("appkey","08158bf9f09b919790a63f10c381be52")
                .queryParam("model","MHA-AL00")
                .queryParam("nonce","a5f9ef33-6531-42e3-a62a-4bf306ff31ef")
                .queryParam("clienttype","SDK")
                .queryParam("rkey","Qd89swl4zIRVDmT4idDbKkJkb%2FfqRBVolQzqyWArad76bYlq6pSwB0Ka4W2tkcR%2BtW16mUvVpgzw%0ANJU7Q%2FG3bRRXtIptJyTdBFEXbtWT6rayL1x1dLhU7wbntjHqlP7h49Mshuu5ixFYckEiPtrW9mBi%0A%2F2AbZPsI9MAeI3cjrIQ%3D%0A")
                .queryParam("signid","qSe6qEbFfJT5Rly1W0AQzhpxTS3amr18VDsNJKovPD887TEXX9XFdA%3D%3D%0A")
                .queryParam("statusCode",statusCode)
        .when()
                .log().all()
                .get("http://open.mgame.360.cn/ad/welfare")
        .then().log().all()
                .statusCode(200);
    }

    @Test
    public void testPostJson(){
        HashMap<String, Object> map=new HashMap<String, Object>();
        map.put("a",1);
        map.put("b",2);

        given()
                .contentType(ContentType.JSON)
                .body(map)
        .when()
                .post("http://www.baidu.com")
        .then()
                .log().all()
                .statusCode(302);
    }

    @Test
    public void testRestAssured(){

        given()
                .spec(requestSpecification)

                .when()
                        .get("/mobile/user_config_v3")
                .then()
                        .log().all()
                .time(lessThan(2L), TimeUnit.SECONDS)
                .time(lessThan(2000L))//单位秒
                .spec(responseSpecification);
    }

}


