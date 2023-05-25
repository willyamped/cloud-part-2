import json

with open("frontend/src/data/try.geo.json") as geojson:
    data = json.load(geojson)
    # for i, f in enumerate(data['features']):
    #     print(f"{i}: {f['properties']}")
    print(data['features'][0]['properties'])
