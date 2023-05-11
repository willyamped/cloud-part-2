package com.javadeveloperzone;

import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JSonReader {

	public static void main(String[] args) {
		String json = "{\r\n" + "  \"id\": \"1492722498617675778\",\r\n" + "  \"key\": [\r\n" + "    2022,\r\n"
				+ "    2,\r\n" + "    13,\r\n" + "    \"1492722498617675778\",\r\n" + "    \"849944076770213889\",\r\n"
				+ "    \"1492722498617675778\"\r\n" + "  ],\r\n" + "  \"value\": {\r\n"
				+ "    \"tags\": \"savas|Ukraine|Russia|NATO\",\r\n"
				+ "    \"tokens\": \"Ukrayna|gaz|verenler|imdi|rusya|galini|seyredecekler|Kazanan|sadece|silah|sat|lar|olacak|ocuklar|lecek\"\r\n"
				+ "  },\r\n" + "  \"doc\": {\r\n" + "    \"_id\": \"1492722498617675778\",\r\n"
				+ "    \"_rev\": \"1-244f0a980cfd21443f11f9ece97578ce\",\r\n" + "    \"data\": {\r\n"
				+ "      \"author_id\": \"849944076770213889\",\r\n" + "      \"context_annotations\": [\r\n"
				+ "        {\r\n" + "          \"domain\": {\r\n" + "            \"id\": \"88\",\r\n"
				+ "            \"name\": \"Political Body\",\r\n"
				+ "            \"description\": \"A section of a government, like The Supreme Court\"\r\n"
				+ "          },\r\n" + "          \"entity\": {\r\n"
				+ "            \"id\": \"1016738021939351558\",\r\n"
				+ "            \"name\": \"North Atlantic Treaty Organization\",\r\n"
				+ "            \"description\": \"North Atlantic Treaty Organization\"\r\n" + "          }\r\n"
				+ "        }\r\n" + "      ],\r\n" + "      \"conversation_id\": \"1492722498617675778\",\r\n"
				+ "      \"created_at\": \"2022-02-13T04:49:11.000Z\",\r\n" + "      \"entities\": {\r\n"
				+ "        \"hashtags\": [\r\n" + "          {\r\n" + "            \"start\": 115,\r\n"
				+ "            \"end\": 121,\r\n" + "            \"tag\": \"savas\"\r\n" + "          },\r\n"
				+ "          {\r\n" + "            \"start\": 122,\r\n" + "            \"end\": 130,\r\n"
				+ "            \"tag\": \"Ukraine\"\r\n" + "          },\r\n" + "          {\r\n"
				+ "            \"start\": 131,\r\n" + "            \"end\": 138,\r\n"
				+ "            \"tag\": \"Russia\"\r\n" + "          },\r\n" + "          {\r\n"
				+ "            \"start\": 139,\r\n" + "            \"end\": 144,\r\n"
				+ "            \"tag\": \"NATO\"\r\n" + "          }\r\n" + "        ],\r\n" + "        \"urls\": [\r\n"
				+ "          {\r\n" + "            \"start\": 145,\r\n" + "            \"end\": 168,\r\n"
				+ "            \"url\": \"https://t.co/1Bh2qzxinK\",\r\n"
				+ "            \"expanded_url\": \"https://twitter.com/SungunDincer/status/1492722498617675778/photo/1\",\r\n"
				+ "            \"display_url\": \"pic.twitter.com/1Bh2qzxinK\"\r\n" + "          }\r\n"
				+ "        ]\r\n" + "      },\r\n" + "      \"geo\": {},\r\n" + "      \"lang\": \"tr\",\r\n"
				+ "      \"public_metrics\": {\r\n" + "        \"retweet_count\": 0,\r\n"
				+ "        \"reply_count\": 0,\r\n" + "        \"like_count\": 0,\r\n"
				+ "        \"quote_count\": 0\r\n" + "      },\r\n"
				+ "      \"text\": \"Ukrayna'ya gaz verenler şimdi rusya işgalini seyredecekler\\nKazanan sadece silah satıcıları olacak \\nÇocuklar ölecek\\n#savas #Ukraine #Russia #NATO https://t.co/1Bh2qzxinK\",\r\n"
				+ "      \"sentiment\": 0\r\n" + "    },\r\n" + "    \"matching_rules\": [\r\n" + "      {\r\n"
				+ "        \"id\": \"1492196925306593281\",\r\n" + "        \"tag\": \"\"\r\n" + "      }\r\n"
				+ "    ]\r\n" + "  }\r\n" + "}";
			
		System.out.println(filerJSONString(json,"lock"));
	}

	private static boolean filerJSONString(String json,String searchString) {
		JSONObject object = (JSONObject) JSONValue.parse(json);

		Set<String> keySet = object.keySet();
		for (String key : keySet) {
			Object value = object.get(key);
			if (key.equals("value")) {
				JSONObject valueObj = (JSONObject) JSONValue.parse(value.toString());
				Set<String> keySet1 = valueObj.keySet();
				for (String key1 : keySet1) {
					Object value1 = valueObj.get(key1);

					System.out.printf("%s=%s (%s)\n", key1, value1, value1.getClass().getSimpleName());
					if (key1.equals("tokens") && value1.toString().contains(searchString)) {
						return true;
					} else {
						return false;
					}

				}
			}

		}
		return false;
	}

}
