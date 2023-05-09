from mastodon import Mastodon, MastodonNotFoundError, MastodonRatelimitError, StreamListener
import csv, os, time, json

with open('data.json','w',encoding='utf-8') as file:
    m = Mastodon(
        api_base_url=f'https://mastodon.au',
        access_token=os.environ['MASTODON_ACCESS_TOKEN']
    )
    class Listener(StreamListener):
        def on_update(self, status):
            obj = json.loads(json.dumps(status, indent=2, sort_keys=True, default=str))
            print(obj['content'])
            file.write(json.dumps(obj, indent=2, ensure_ascii=False))
            file.write('\r\n')
    obj = m.stream_public(Listener()) 

file.close()     