import { useRef, useEffect, useState } from 'react';
import { select, geoPath, geoMercator, geoEqualEarth, scaleLinear, zoom } from 'd3';
import mapData from './../data/STE_2021_AUST_GDA20201.json';

function Map({ data, property }) {
  const svgRef = useRef();
  const [selectedCountry, setSelectedCountry] = useState(null);

  useEffect(() => {
    const svg = select(svgRef.current);

    const colorScale = scaleLinear()
      .domain([0, Math.max(...data.map(({ value }) => value['Hospital']))])
      .range(['#ccc', 'red']);

    const { width, height } = svgRef.current.getBoundingClientRect();
    // const width = 1000;
    // const height = 600;

    const projection = geoMercator()
      .fitSize([width, height], mapData)
    const pathGenerator = geoPath().projection(projection);

    const getCount = (state) => {
      const found = data.filter(obj => obj['key'] === state)
      return found.length > 0 ? found[0]['value']['Hospital'] : 0;
    }

    svg.select('.map')
      .selectAll('.country')
      .data(mapData.features)
      .join('path')
      .attr('class', 'country')
      .on('mouseover', (event, feature) => {
        setSelectedCountry(feature);
      })
      .on('mouseout', (event, feature) => {
        setSelectedCountry(null);
      })
      // .attr('fill', 'None')
      .attr('fill', feature => colorScale(getCount(feature['properties']['STE_NAME21'])))
      .attr('d', pathGenerator);

    svg.select('.label')
      .data([selectedCountry])
      .join('text')
      .attr('class', 'label')
      .text(feature => feature && `${feature?.['properties']?.['STE_NAME21']}: ${getCount(feature?.['properties']?.['STE_NAME21'])}`)
      .attr('x', 10)
      .attr('y', 25);

    svg.call(zoom()
      .translateExtent([[0, 0], [width, height]])
      .scaleExtent([1, 4])
      .on('zoom', (event) => {
        svg.select('.map').attr('transform', event.transform)
      }));

  }, [data, property, selectedCountry]);

  return (
    <svg ref={svgRef} style={{ width: '70vw', minHeight: '90vh', border: '1px solid black', margin: '50px', padding: '10px'}}>
      <g className="map" />
      <text className="label" />
    </svg>
  );
}

export default Map;
