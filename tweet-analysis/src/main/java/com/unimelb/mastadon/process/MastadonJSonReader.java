package com.unimelb.mastadon.process;

import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class MastadonJSonReader {

	public static void main(String[] args) {
		String json = "{\r\n"
				+ "  \"account\": {\r\n"
				+ "    \"acct\": \"ESPNNBA@sportsbots.xyz\",\r\n"
				+ "    \"avatar\": \"https://o.mastodon.au/cache/accounts/avatars/109/746/540/539/667/044/original/ae5e8681a3444692.jpg\",\r\n"
				+ "    \"avatar_static\": \"https://o.mastodon.au/cache/accounts/avatars/109/746/540/539/667/044/original/ae5e8681a3444692.jpg\",\r\n"
				+ "    \"bot\": true,\r\n"
				+ "    \"created_at\": \"2009-09-15 00:00:00+00:00\",\r\n"
				+ "    \"discoverable\": true,\r\n"
				+ "    \"display_name\": \"NBA on ESPN :verified_business: 🤖\",\r\n"
				+ "    \"emojis\": [\r\n"
				+ "      {\r\n"
				+ "        \"shortcode\": \"verified_business\",\r\n"
				+ "        \"static_url\": \"https://o.mastodon.au/cache/custom_emojis/images/000/074/958/static/e9d3cebffee06252.png\",\r\n"
				+ "        \"url\": \"https://o.mastodon.au/cache/custom_emojis/images/000/074/958/original/e9d3cebffee06252.png\",\r\n"
				+ "        \"visible_in_picker\": true\r\n"
				+ "      }\r\n"
				+ "    ],\r\n"
				+ "    \"fields\": [\r\n"
				+ "      {\r\n"
				+ "        \"name\": \"Twitter\",\r\n"
				+ "        \"value\": \"<a href=\\\"https://twitter.com/ESPNNBA\\\" rel=\\\"nofollow noopener noreferrer\\\" target=\\\"_blank\\\"><span class=\\\"invisible\\\">https://</span><span class=\\\"\\\">twitter.com/ESPNNBA</span></a>\",\r\n"
				+ "        \"verified_at\": null\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "        \"name\": \"Twitter Verified\",\r\n"
				+ "        \"value\": \"Business\",\r\n"
				+ "        \"verified_at\": null\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "        \"name\": \"Website\",\r\n"
				+ "        \"value\": \"<a href=\\\"http://espn.go.com/nba/\\\" rel=\\\"nofollow noopener noreferrer\\\" target=\\\"_blank\\\"><span class=\\\"invisible\\\">http://</span><span class=\\\"\\\">espn.go.com/nba/</span><span class=\\\"invisible\\\"></span></a>\",\r\n"
				+ "        \"verified_at\": null\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "        \"name\": \"Managed by\",\r\n"
				+ "        \"value\": \"<span class=\\\"h-card\\\"><a href=\\\"https://mastodon.social/@sportsbots\\\" class=\\\"u-url mention\\\" rel=\\\"nofollow noopener noreferrer\\\" target=\\\"_blank\\\">@<span>sportsbots</span></a></span>\",\r\n"
				+ "        \"verified_at\": null\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "        \"name\": \"Retention\",\r\n"
				+ "        \"value\": \"<span class=\\\"h-card\\\">90 days</span>\",\r\n"
				+ "        \"verified_at\": null\r\n"
				+ "      }\r\n"
				+ "    ],\r\n"
				+ "    \"followers_count\": 8540000,\r\n"
				+ "    \"following_count\": 600,\r\n"
				+ "    \"group\": false,\r\n"
				+ "    \"header\": \"https://mastodon.au/headers/original/missing.png\",\r\n"
				+ "    \"header_static\": \"https://mastodon.au/headers/original/missing.png\",\r\n"
				+ "    \"id\": 109746540539667044,\r\n"
				+ "    \"last_status_at\": \"2023-05-08 00:00:00\",\r\n"
				+ "    \"locked\": false,\r\n"
				+ "    \"note\": \"<p>Unofficial bot that mirrors NBA on ESPN’s Twitter feed.</p><p>Official Twitter account of the NBA on ESPN, home of the NBA Draft and the NBA Finals.</p>\",\r\n"
				+ "    \"statuses_count\": 312,\r\n"
				+ "    \"url\": \"https://sportsbots.xyz/users/ESPNNBA\",\r\n"
				+ "    \"username\": \"ESPNNBA\"\r\n"
				+ "  },\r\n"
				+ "  \"card\": null,\r\n"
				+ "  \"content\": \"<p>Despite the loss, Horford had Embiid on lockdown 😮</p>\",\r\n"
				+ "  \"created_at\": \"2023-05-08 13:40:08+00:00\",\r\n"
				+ "  \"edited_at\": null,\r\n"
				+ "  \"emojis\": [],\r\n"
				+ "  \"favourites_count\": 0,\r\n"
				+ "  \"filtered\": [],\r\n"
				+ "  \"id\": 110333346942769880,\r\n"
				+ "  \"in_reply_to_account_id\": null,\r\n"
				+ "  \"in_reply_to_id\": null,\r\n"
				+ "  \"language\": \"en\",\r\n"
				+ "  \"media_attachments\": [\r\n"
				+ "    {\r\n"
				+ "      \"blurhash\": \"UYGum{MwIUnmICMyX8bu0K-;jGjFNFxuoIjF\",\r\n"
				+ "      \"description\": null,\r\n"
				+ "      \"id\": 110333346865341592,\r\n"
				+ "      \"meta\": {\r\n"
				+ "        \"original\": {\r\n"
				+ "          \"aspect\": 0.8,\r\n"
				+ "          \"height\": 1200,\r\n"
				+ "          \"size\": \"960x1200\",\r\n"
				+ "          \"width\": 960\r\n"
				+ "        },\r\n"
				+ "        \"small\": {\r\n"
				+ "          \"aspect\": 0.8003731343283582,\r\n"
				+ "          \"height\": 536,\r\n"
				+ "          \"size\": \"429x536\",\r\n"
				+ "          \"width\": 429\r\n"
				+ "        }\r\n"
				+ "      },\r\n"
				+ "      \"preview_remote_url\": null,\r\n"
				+ "      \"preview_url\": \"https://o.mastodon.au/cache/media_attachments/files/110/333/346/865/341/592/small/e5c48f0af3f479ab.jpg\",\r\n"
				+ "      \"remote_url\": \"https://pbs.twimg.com/media/FvnCj3JXwAIrode.jpg\",\r\n"
				+ "      \"text_url\": null,\r\n"
				+ "      \"type\": \"image\",\r\n"
				+ "      \"url\": \"https://o.mastodon.au/cache/media_attachments/files/110/333/346/865/341/592/original/e5c48f0af3f479ab.jpg\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"mentions\": [],\r\n"
				+ "  \"poll\": null,\r\n"
				+ "  \"reblog\": null,\r\n"
				+ "  \"reblogs_count\": 0,\r\n"
				+ "  \"replies_count\": 0,\r\n"
				+ "  \"sensitive\": false,\r\n"
				+ "  \"spoiler_text\": \"\",\r\n"
				+ "  \"tags\": [],\r\n"
				+ "  \"uri\": \"https://sportsbots.xyz/users/ESPNNBA/statuses/1655568265098285062\",\r\n"
				+ "  \"url\": \"https://twitter.com/ESPNNBA/status/1655568265098285062\",\r\n"
				+ "  \"visibility\": \"public\"\r\n"
				+ "}\r\n"
				+ "";

		System.out.println(filterJSONString(json, "lock"));
	}

	private static boolean filterJSONString(String json, String searchString) {
		JSONObject object = (JSONObject) JSONValue.parse(json);

		Set<String> keySet = object.keySet();
		for (String key : keySet) {

			if (key.equals("content")) {
				Object value = object.get(key);
				if (value.toString().contains(searchString)) {
					return true;
				}
			}

		}

		return false;
	}

}
