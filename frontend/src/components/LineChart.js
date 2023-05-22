import { useRef, useEffect } from "react";
import { select, axisBottom, axisLeft, scaleLinear, line } from "d3";

const margin = { top: 110, right: 150, bottom: 80, left: 150 };
const WIDTH = 1200 - margin.left - margin.right;
const HEIGHT = 600 - margin.top - margin.bottom;

function LineChart({ data, title, xLabel, yLabel }) {
  const svgRef = useRef();

  useEffect(() => {
    const svg = select(svgRef.current)
      .style("width", WIDTH + margin.left + margin.right)
      .style("height", HEIGHT + margin.top + margin.bottom);

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
      .domain([0, data.length - 1])
      .range([0, WIDTH])
    const xAxis = axisBottom(xScale)
      .ticks(data.length)
      .tickFormat(index => data[index]['key']);
    svg.select(".x-axis")
      .style("transform", `translateY(${HEIGHT}px)`)
      .call(xAxis);

    // x-label
    svg.select(".x-label")
      .attr("transform", `translate(${WIDTH / 2}, ${HEIGHT + margin.bottom / 2})`)
      .style("text-anchor", "middle")
      .text(xLabel);
    
    // y-axis
    const yScale = scaleLinear()
      .domain([0, Math.max(...data.map(({ value }) => value).flat())])
      .range([HEIGHT, 0]);
    svg.select(".y-axis")
      .call(axisLeft(yScale));

    // y-label
    svg.select(".y-label")
      .attr("transform", "rotate(-90)")
      .attr("x", -(HEIGHT / 2))
      .attr("y", -(margin.left / 2))
      .style("text-anchor", "middle")
      .text(yLabel);
    
    // lines
    const myLine = line()
      .x((_, i) => xScale(i))
      .y(({ value }) => yScale(value));
    svg.select(".chart")
      .selectAll(".line")
      .data([data])
      .join("path")
      .attr("class", "line")
      .attr("d", myLine)
      .attr("fill", "none")
      .attr("stroke", "#002884")
  }, [data, title, xLabel, yLabel]);

  return (
    <svg ref={svgRef} style={{ margin: '50px', border: '1px solid black' }}>
      <g className='chart' transform={`translate(${margin.left}, ${margin.top})`}>
        <text className='title' />
        <g className="x-axis" />
        <text className='x-label' />
        <g className="y-axis" />
        <text className='y-label' />
      </g>
    </svg>
  );
}

export default LineChart;
