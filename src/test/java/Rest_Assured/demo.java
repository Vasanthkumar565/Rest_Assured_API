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

public class demo {

	String base_url = "https://api.github.com";
	String content_sha;
	String update_content_sha;
	int autolink_id;
	String content_sha1;

	@Test(priority = 0)
	public void Create_Repository_for_Authenticated_User() {
		
		JSONObject json_obj = new JSONObject();
		json_obj.put("name", "vasanth");

		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json").body(json_obj).when().post("/user/repos").then()
				.statusCode(201).log().all();
	}
	@Test(priority = 1)
	public void Create_a_File_Content() {
		RestAssured.baseURI = base_url;
		String commitMessage = "my commit message";
		String committerName = "Monalisa Octocat";
		String committerEmail = "octocat@github.com";
		String fileContent = "bXkgbmV3IGZpbGUgY29udGVudHM=";

		Response resp=given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json")
				.body("{\"message\":\"" + commitMessage + "\",\"committer\":{\"name\":\"" + committerName
						+ "\",\"email\":\"" + committerEmail + "\"},\"content\":\"" + fileContent + "\"}")
				.when().put("/repos/Vasanthkumar565/vasanth/contents/moolya_vasanth.txt").then().statusCode(201).log()
				.all().extract()
				.response();
		System.out.println("sha for creating a file ::" + resp.path("content.sha"));
		content_sha = resp.path("content.sha").toString();
	}

	@Test(priority = 2)
	public void Update_a_File_Content() {

		String commitMessage = "my commit message";
		String committerName = "Monalisa Octocat";
		String committerEmail = "octocat@github.com";
		String fileContent = "bXkgbmV3IGZpbGUgY29udGVudHM=";
		System.out.println("content_sha" + content_sha);
		String sha = content_sha;

		Response respo=given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json")
				.body("{\"message\":\"" + commitMessage + "\",\"committer\":{\"name\":\"" + committerName
						+ "\",\"email\":\"" + committerEmail + "\"},\"content\":\"" + fileContent + "\",\"sha\":\""
						+ sha + "\"}")
				.when().put("/repos/Vasanthkumar565/vasanth/contents/moolya000_vasanth.txt").then().statusCode(201)
				.log().all().extract()
				.response();
		System.out.println("sha for update a file ::" + respo.path("content.sha"));
		//content_sha1 = respo.path("content.sha").toString();
	}

	@Test(priority = 3)
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
				.when().delete("/repos/Vasanthkumar565/vasanth/contents/moolya000_vasanth.txt").then().statusCode(200)
				.log().all();
	}
	@Test(priority = 4)
	public void Delete_a_Repository() {

		JSONObject json_obj = new JSONObject();
		json_obj.put("name", "vasanth");

		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.header("Content-Type", "application/json").body(json_obj).when()
				.delete("/repos/Vasanthkumar565/vasanth").then().statusCode(204).log().all();
	}
	@Test(priority = 9)
	public void create_a_file_content() {
		String content = "bXkgbmV3IGZpbGUgY29dGVudHM=";
		String base64Content = Base64.getEncoder().encodeToString(content.getBytes());

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", "my commit message");
		JSONObject committer = new JSONObject();
		committer.put("name", "Monalisa Octocat");
		committer.put("email", "octocat@github.com");
		jsonObject.put("committer", committer);
		jsonObject.put("content", base64Content);

		Response resp = given().baseUri(base_url)
				.header("Authorization", "Bearer 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")
				.contentType("application/x-www-form-urlencoded").body(jsonObject.toJSONString()).when()
				.put("/repos/Vasanthkumar565/vasanth999/contents/moolya_vasanth.txt").then().log().all().extract()
				.response();

		System.out.println("sha for creating a file ::" + resp.path("content.sha"));
		content_sha = resp.path("content.sha").toString();
	}

	@Test(priority = 10)
	public void update_a_file_content() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", "a new commit message");
		JSONObject committer = new JSONObject();
		committer.put("name", "Monalisa Octocat");
		committer.put("email", "octocat@github.com");
		jsonObject.put("committer", committer);
		jsonObject.put("content", "bXkgdXBkYXRlZCBmaWxlIGNvbnRlbnRz");
		System.out.println("content_sha" + content_sha);
		jsonObject.put("sha", content_sha);

		Response resp = given().baseUri(base_url)
				.header("Authorization", "Bearer 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")
				.contentType(ContentType.JSON).body(jsonObject.toJSONString()).when()
				.put("/repos/Vasanthkumar565/vasanth999/contents/moolya01_vasanth.txt").then().log().all().extract()
				.response();

		// String responsive=resp.asString();

		System.out.println("sha path for updating a file ::" + resp.path("content.sha"));
		update_content_sha = resp.path("content.sha").toString();
	}

	@Test(priority = 12)
	public void delete_a_file_content() {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("message", "a new commit message");

		JSONObject committer = new JSONObject();
		committer.put("name", "Monalisa Octocat");
		committer.put("email", "octocat@github.com");

		jsonObject.put("committer", committer);

		jsonObject.put("content", "bXkgdXBkYXRlZCBmaWxlIGNvbnRlbnRz");

		System.out.println("updated content shar" + update_content_sha);
		jsonObject.put("sha", update_content_sha);

		given().baseUri(base_url).header("Authorization", "Bearer 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")
				.contentType(ContentType.JSON).body(jsonObject.toJSONString()).when()
				.delete("/repos/Vasanthkumar565/vasanth999/contents/moolya01_vasanth.txt").then().log().all();
	}

}
