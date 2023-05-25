import json
import shapefile

with shapefile.Reader("shp/state_shp/STE_2021_AUST_GDA2020") as shp:
    geojson_data = shp.__geo_interface__

with open("frontend/src/data/state.geo.json", "w") as geojson:
    json.dump(geojson_data, geojson)
