import { useRef, useEffect, useState } from 'react';
import { select, geoPath, geoMercator, min, max, scaleLinear, zoom } from 'd3';

const ZOOM_IN_MAX = 8
const ZOOM_OUT_MAX = 1

function Map({ data, property }) {
  const svgRef = useRef();
  const gRef = useRef();
  const [selectedCountry, setSelectedCountry] = useState(null);

  // will be called initially and on every data change
  useEffect(() => {
    const svg = select(svgRef.current);

    const minProp = min(data.features, feature => feature.properties[property]);
    const maxProp = max(data.features, feature => feature.properties[property]);
    const colorScale = scaleLinear()
      .domain([minProp, maxProp])
      .range(['#ccc', 'red']);

    const { width, height } = svgRef.current.getBoundingClientRect();

    // projects geo-coordinates on a 2D plane
    const projection = geoMercator()
      .fitSize([width, height], data)
      .precision(100);

    // takes geojson data,
    // transforms that into the d attribute of a path element
    const pathGenerator = geoPath().projection(projection);

    const g = select(gRef.current);

    // render each country
    g
      .selectAll('.country')
      .data(data.features)
      .join('path')
      .on('click', (event, feature) => {
        setSelectedCountry(selectedCountry === feature ? null : feature);
      })
      .attr('class', 'country')
      .transition()
      .attr('fill', feature => colorScale(feature.properties[property]))
      .attr('d', feature => pathGenerator(feature));

    // render text
    g
      .selectAll('.label')
      .data([selectedCountry])
      .join('text')
      .attr('class', 'label')
      .text(
        feature =>
          feature &&
          feature.properties.name +
            ': ' +
            feature.properties[property].toLocaleString()
      )
      .attr('x', 10)
      .attr('y', 25);

    svg.call(zoom()
      .translateExtent([[0, 0], [width, height]])
      .scaleExtent([ZOOM_OUT_MAX, ZOOM_IN_MAX])
      .on('zoom', (event) => {
        g.attr('transform', event.transform)
      }));

  }, [data, property, selectedCountry]);

  return (
    <svg ref={svgRef} style={{ width: '90vw', height: '90vh' }}>
      <g ref={gRef}></g>
    </svg>
  );
}

export default Map;
