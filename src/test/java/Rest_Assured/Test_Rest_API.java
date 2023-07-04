package Rest_Assured;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.json.simple.JSONObject;

import io.restassured.RestAssured;

import io.restassured.response.Response;

public class Test_Rest_API {

	String base_url = "https://api.github.com";

	String content_sha;
	String update_content_sha;
	int Auto_id;

	@Test(priority = 0)
	public void Create_Repository_for_Authenticated_User() {
		RestAssured.baseURI = base_url;

		JSONObject json_obj = new JSONObject();
		json_obj.put("name", "vasanth");

		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json").body(json_obj).when().post("/user/repos").then()
				.statusCode(201).log().all();
	}

	@Test(priority = 1)
	public void Update_a_Repository() {

		JSONObject json_obj = new JSONObject();
		json_obj.put("name", "vasanth999");
		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json").body(json_obj.toJSONString()).when()
				.patch("/repos/Vasanthkumar565/vasanth").then().statusCode(200).log().all();
	}

	@Test(priority = 2)
	public void Get_a_Repository() {

		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json").when().get("/repos/Vasanthkumar565/vasanth999").then()
				.statusCode(200).log().all();
	}

	@Test(priority = 3)
	public void Create_a_Fork() {

		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json").when().post("repos/navinreddy20/Javacode/forks").then()
				.statusCode(202).log().all();
	}

	@Test(priority = 4)
	public void List_of_Forks() {

		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json").when().get("/repos/navinreddy20/Javacode/forks").then()
				.statusCode(200).log().all();
	}

	@Test(priority = 5)
	public void List_Repositories_of_User() {

		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json").when().get("/users/Vasanthkumar565/repos").then()
				.statusCode(200).log().all();
	}

	@Test(priority = 6)
	public void List_Repositories_of_Language() {

		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json").when()
				.get("/repos/Vasanthkumar565/souledstore-vasanth/languages").then().statusCode(200).log().all();
	}

	@Test(priority = 7)
	public void List_Public_Repositories() {

		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json").when().get("/repositories").then().statusCode(200).log()
				.all();
	}

	@Test(priority = 8)
	public void Create_a_File_Content() {
		RestAssured.baseURI = base_url;
		String commitMessage = "my commit message";
		String committerName = "Monalisa Octocat";
		String committerEmail = "octocat@github.com";
		String fileContent = "bXkgbmV3IGZpbGUgY29udGVudHM=";

		Response resp = given().baseUri(base_url)
				.header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json")
				.body("{\"message\":\"" + commitMessage + "\",\"committer\":{\"name\":\"" + committerName
						+ "\",\"email\":\"" + committerEmail + "\"},\"content\":\"" + fileContent + "\"}")
				.when().put("/repos/Vasanthkumar565/vasanth999/contents/moolya_vasanth.txt").then().statusCode(201)
				.log().all().extract().response();
		System.out.println("sha for creating a file ::" + resp.path("content.sha"));
		content_sha = resp.path("content.sha").toString();
	}

	@Test(priority = 9)
	public void Update_a_File_Content() {

		String commitMessage = "my commit message";
		String committerName = "Monalisa Octocat";
		String committerEmail = "octocat@github.com";
		String fileContent = "bXkgbmV3IGZpbGUgY29udGVudHM=";
		System.out.println("content_sha" + content_sha);
		String sha = content_sha;

		Response respo = given().baseUri(base_url)
				.header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json")
				.body("{\"message\":\"" + commitMessage + "\",\"committer\":{\"name\":\"" + committerName
						+ "\",\"email\":\"" + committerEmail + "\"},\"content\":\"" + fileContent + "\",\"sha\":\""
						+ sha + "\"}")
				.when().put("/repos/Vasanthkumar565/vasanth999/contents/moolyaed_vasanth.txt").then().statusCode(201)
				.log().all().extract().response();
		System.out.println("sha for update a file ::" + respo.path("content.sha"));
	}

	@Test(priority = 10)
	public void Delete_a_File_Content() {
		String commitMessage = "my commit message";
		String committerName = "Monalisa Octocat";
		String committerEmail = "octocat@github.com";
		String fileContent = "bXkgbmV3IGZpbGUgY29udGVudHM=";
		String sha = "0d5a690c8fad5e605a6e8766295d9d459d65de42";

		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json")
				.body("{\"message\":\"" + commitMessage + "\",\"committer\":{\"name\":\"" + committerName
						+ "\",\"email\":\"" + committerEmail + "\"},\"content\":\"" + fileContent + "\",\"sha\":\""
						+ sha + "\"}")
				.when().delete("/repos/Vasanthkumar565/vasanth999/contents/moolyaed_vasanth.txt").then().statusCode(200)
				.log().all();
	}

	@Test(priority = 11)
	public void List_Repository_tags() {

		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json").when().get("/repos/Vasanthkumar565/vasanth999/tags").then()
				.statusCode(200).log().all();
	}

	@Test(priority = 12)
	public void Create_an_AutoLink_Reference_for_a_Repository() {
		String keyPrefix = "TICKET-";
		String urlTemplate = "https://example.com/TICKET?query=<num>";
		boolean isAlphanumeric = true;

		Response respo = given().baseUri(base_url)
				.header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json")
				.body("{\"key_prefix\":\"" + keyPrefix + "\",\"url_template\":\"" + urlTemplate
						+ "\",\"is_alphanumeric\":" + isAlphanumeric + "}")
				.when().post("/repos/Vasanthkumar565/createfile1/autolinks").then().statusCode(201).log().all()
				.extract().response();

		Auto_id = respo.path("id");
	}

	@Test(priority = 13)
	public void Get_an_autolink_reference_for_a_Repository() {

		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json")
				.get("/repos/Vasanthkumar565/createfile1/autolinks/" + Auto_id + "").then().statusCode(200).log().all();
	}

	@Test(priority = 14)
	public void Delete_from_an_autolink_reference_for_a_Repository() {

		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json")
				.delete("/repos/Vasanthkumar565/createfile1/autolinks/+" + Auto_id + "").then().statusCode(204).log()
				.all();
	}

	@Test(priority = 15)
	public void Get_all_Repository_topics() {
		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json").when().get("/repos/Vasanthkumar565/moolya/topics").then()
				.statusCode(200).log().all();
	}

	@Test(priority = 16)
	public void Replace_all_Repository_topics() {
		// List<String> newTopics = Arrays.asList("vasanth1", "reddy1", "moolya1",
		// "api1");
		String json_body = "{\"names\":[\"vasanthreddy\",\"9999\",\"moolya\",\"api\"]}";
		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.contentType(ContentType.JSON).body(json_body).when().put("/repos/Vasanthkumar565/moolya/topics").then()
				.statusCode(200).log().all();
	}

	@Test(priority = 17)
	public void Delete_a_Repository() {

		JSONObject json_obj = new JSONObject();
		json_obj.put("name", "vasanth999");

		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json").body(json_obj).when()
				.delete("/repos/Vasanthkumar565/vasanth999").then().statusCode(204).log().all();
	}

}