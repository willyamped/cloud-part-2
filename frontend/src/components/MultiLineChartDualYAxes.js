import { useRef, useEffect } from "react";
import { select, axisBottom, axisLeft, axisRight, scaleLinear, scaleOrdinal, line, max } from "d3";

const margin = { top: 110, right: 150, bottom: 80, left: 150 };
const WIDTH = 1200 - margin.left - margin.right;
const HEIGHT = 600 - margin.top - margin.bottom;
const colors = ["#002884", "#757ce8"]

function MultiLineChartDualYAxes({ data, title, xLabel, yLeftLabel, yRightLabel }) {
  const svgRef = useRef();

  useEffect(() => {
    const svg = select(svgRef.current)
    .style("width", WIDTH + margin.left + margin.right)
    .style("height", HEIGHT + margin.top + margin.bottom);
    
    if (data && data.length > 1 && data[0]['value'] && data[1]['value']) {
      // title
      svg.select(".title")
        .attr("x", WIDTH / 2)
        .attr("y", -(margin.top / 2))
        .attr("text-anchor", "middle")
        .style("font-size", "20px")
        .style("font-weight", "bold")
        .text(title);

      // x-axis
      const xScale = scaleLinear()
        .domain([0, data[0]['value'].length - 1])
        .range([0, WIDTH])
      const xAxis = axisBottom(xScale)
        .ticks(data[0]['value'].length)
        .tickFormat(index => data[0]['value'][index]["key"]);
      svg.select(".x-axis")
        .style("transform", `translateY(${HEIGHT}px)`)
        .call(xAxis);

      // x-label
      svg.select(".x-label")
        .attr("transform", `translate(${WIDTH / 2}, ${HEIGHT + margin.bottom / 2})`)
        .style("text-anchor", "middle")
        .text(xLabel);
      
      // y-axis
      const yLeftScale = scaleLinear()
        .domain([0, max(data[0]["value"].map(({ value }) => value))])
        .range([HEIGHT, 0]);
      svg.select(".y-left-axis")
        .style("color", colors[0])
        .call(axisLeft(yLeftScale));
      const yRightScale = scaleLinear()
        .domain([0, max(data[1]["value"].map(({ value }) => value))])
        .range([HEIGHT, 0]);
      svg.select(".y-right-axis")
        .style("transform", `translateX(${WIDTH}px)`)
        .style("color", colors[1])
        .call(axisRight(yRightScale));

      // y-label
      svg.select(".y-left-label")
        .attr("transform", "rotate(-90)")
        .attr("x", -(HEIGHT / 2))
        .attr("y", -(margin.left / 2))
        .style("text-anchor", "middle")
        .style("fill", colors[0])
        .text(yLeftLabel);
      svg.select(".y-right-label")
        .attr("transform", "rotate(90)")
        .attr("x", HEIGHT / 2)
        .attr("y", -(WIDTH + margin.right / 2))
        .style("text-anchor", "middle")
        .style("fill", colors[1])
        .text(yRightLabel);

      // colors
      const color = scaleOrdinal()
        .domain(data.map(({ key }) => key))
        .range(colors)

      // legend
      svg.select(".legend")
        .selectAll(".dot")
        .data(data.map(({ key}) => key))
        .join("circle")
        .attr("class", "dot")
        .attr("cx", WIDTH)
        .attr("cy", (_, i) => -(3 * margin.top / 5) + i * 25)
        .attr("r", 7)
        .style("fill", key => color(key))

      svg.select(".legend")
        .selectAll(".label")
        .data(data.map(({ key}) => key))
        .join("text")
        .attr("class", "label")
        .attr("x", WIDTH + 20)
        .attr("y", (_, i) => -(3 * margin.top / 5) + i * 25)
        .text(key => key)
        .attr("text-anchor", "left")
        .style("alignment-baseline", "middle")
      
      // lines
      const myLeftLine = line()
        .x((_, i) => xScale(i))
        .y(({ value }) => yLeftScale(value));
      const myRightLine = line()
        .x((_, i) => xScale(i))
        .y(({ value }) => yRightScale(value));
      svg.select(".chart")
        .selectAll(".lines")
        .data(data)
        .join("g")
        .attr("class", "lines")
        .selectAll(".line")
        .data(data => [data])
        .join("path")
        .attr("class", "line")
        .attr("d", ({ key, value }) => key === data.map(({ key }) => key)[0] ? myLeftLine(value) : myRightLine(value))
        .attr("fill", "none")
        .attr("stroke", color)
    }
  }, [data, title, xLabel, yLeftLabel, yRightLabel]);

  return (
    <svg ref={svgRef} style={{ margin: "50px", border: "1px solid black" }}>
      <g className="chart" transform={`translate(${margin.left}, ${margin.top})`}>
        <text className="title" />
        <g className="x-axis" />
        <text className="x-label" />
        <g className="y-left-axis" />
        <text className="y-left-label" />
        <g className="y-right-axis" />
        <text className="y-right-label" />
        <g className="legend" />
      </g>
    </svg>
  );
}

export default MultiLineChartDualYAxes;
