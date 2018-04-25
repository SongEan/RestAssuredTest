import org.junit.Test;

import javax.xml.ws.Response;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.hamcrest.Matchers.*;

public class BaiduTest {
    @Test
    public void testJson(){
        given()
                .when()
                        .log().all()
                        .get("http://127.0.0.1:8000/jiekou.json")
                .then()
                        .log().all()
                        .body("lotto.lottoId",equalTo(5))
                        .body("lotto.winners.winnerId",hasItems(23,54))
                        .body("lotto.winners.numbers[0][1]",equalTo(45));
    }

    @Test
    public void testXJson(){
        given()
                .when()
                        .log().all()
                        .get("http://127.0.0.1:8000/jiekou2.json")
                .then()
                        .log().all()
                        .body("store.book.author",hasItems("Nigel Rees"))
                        .body("store.book[0].author",equalTo("Nigel Rees"))
                        .body("store.book[-1].price",is(22.99f));

    }

    @Test
    public void testXml(){
       // Response response =
           given()
                .when()
                        .log().all().get("http://127.0.0.1:8000/jiekou3.xml")
                .then()
                        .log().all()
                        .body("shopping.category.item[0]",equalTo("Chocolate"))
                        .body("shopping.category.item.size()",equalTo(5))
                        .body("shopping.category.findAll{it.@type=='supplies'}.size()",equalTo(1))
                        .body("shopping.category.item.findAll{it.@quantity==4}",equalTo("Pens"))
                        .body("shopping.category.item.findAll{it.price==20}.name",equalTo("Paper"))
                        .body("**.findAll{it.price==20}.name",equalTo("Paper"))
                        .body("shopping.category.item.findAll{it.name=='Paper'}.price",equalTo("20"))
                        .body("shopping.category.item.find{item->item.price==20}.price",equalTo("20"));
        //.extract().response();
        System.out.println();
    }

    @Test
    public void testFind(){
        given()
                .when()
                        .log().all()
                        .get("http://127.0.0.1:8000/jiekou3.xml")
                .then()
                        .body("shopping.category.item.find{item->item.price==20}.price",equalTo("20"));
    }
}

