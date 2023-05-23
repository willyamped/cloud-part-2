import { useRef, useEffect } from "react";
import { select, scaleOrdinal, pie, arc } from "d3";

const margin = { top: 110, right: 150, bottom: 80, left: 150 };
const WIDTH = 1200 - margin.left - margin.right;
const HEIGHT = 600 - margin.top - margin.bottom;
const colors = ["#002884", "#3f50b5", "#757ce8"]

function PieChart({ data, title }) {
  const svgRef = useRef();

  useEffect(() => {
    const svg = select(svgRef.current)
      .style("width", WIDTH + margin.left + margin.right)
      .style("height", HEIGHT + margin.top + margin.bottom);

    if (data) {
      // title
      svg.select(".title")
        .attr("x", WIDTH / 2)
        .attr("y", -(margin.top / 2))
        .attr("text-anchor", "middle")
        .style("font-size", "20px")
        .style("font-weight", "bold")
        .text(title);

      // color
      const color = scaleOrdinal()
        .range(colors)

      // pie
      const radius = HEIGHT / 2 - margin.top - margin.bottom;
      const myPie = pie()
        .value(({ value }) => {
          console.log('value', value)
          return value})
      const myArc = arc()
        .innerRadius(0)
        .outerRadius(radius);

      svg.select(".chart")
        .selectAll(".pie")
        .data(myPie(data))
        .join("path")
        .attr("class", "pie")
        .attr("d", myArc)
        .attr("fill", data => {
          console.log(data['data']['key'])
          return color(data['data']['key'])})
        .attr("stroke", "black")
    }
  }, [data, title]);

  return (
    <svg ref={svgRef} style={{ margin: "50px", border: "1px solid black" }}>
      <g className="chart" transform={`translate(${margin.left}, ${margin.top})`}>
        <text className="title" />
      </g>
    </svg>
  );
}

export default PieChart;
