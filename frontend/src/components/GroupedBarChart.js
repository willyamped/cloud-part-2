import { useRef, useEffect } from "react";
import { select, axisBottom, axisLeft, scaleLinear, scaleBand, scaleOrdinal } from "d3";

const margin = { top: 60, right: 100, bottom: 80, left: 150 };
const WIDTH = 1200 - margin.left - margin.right;
const HEIGHT = 500 - margin.top - margin.bottom;

const generateRandomColor = () => `#${Math.floor(Math.random() * 16777215).toString(16)}`;

function GroupedBarChart({ data, title, xLabel, yLabel }) {
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
    const xScale = scaleBand()
      .domain(data.map(({ key }) => key))
      .range([0, WIDTH])
      .padding(0.5);
    const xSubScale = scaleBand()
      .domain(data.map(({ value }) => Object.keys(value)).flat())
      .range([0, xScale.bandwidth()]);
    svg.select(".x-axis")
      .style("transform", `translateY(${HEIGHT}px)`)
      .call(axisBottom(xScale));

    // x-label
    svg.select(".x-label")
      .attr("transform", `translate(${WIDTH / 2}, ${HEIGHT + margin.bottom / 2})`)
      .style("text-anchor", "middle")
      .text(xLabel);
    
    // y-axis
    const yScale = scaleLinear()
      .domain([0, Math.max(...data.map(({ value }) => Object.values(value)).flat())])
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

    // colors
    const color = scaleOrdinal()
      .domain(Object.keys(data[0].value))
      .range(Object.keys(data[0].value).map(generateRandomColor))

    // legend
    svg.select(".legend")
      .selectAll(".dot")
      .data(Object.keys(data[0].value))
      .join("circle")
      .attr("class", "dot")
      .attr("cx", WIDTH - 50)
      .attr("cy", (_, i) => 10 + i * 25)
      .attr("r", 7)
      .style("fill", key => color(key))

    svg.select(".legend")
      .selectAll(".label")
      .data(Object.keys(data[0].value))
      .join("text")
      .attr("class", "label")
      .attr("x", WIDTH - 30)
      .attr("y", (_, i) => 10 + i * 25)
      .text(key => key)
      .attr("text-anchor", "left")
      .style("alignment-baseline", "middle")
    
    // bars
    svg.select(".chart")
      .selectAll(".bars")
      .data(data)
      .join("g")
      .attr("class", "bars")
      .style("transform", ({ key }) => `translateX(${xScale(key)}px)`)
      .selectAll(".bar")
      .data(({ value }) => Object.keys(value).map(key2 => ({"key2": key2, "value2": value[key2]})))
      .join("rect")
      .attr("class", "bar")
      .style("transform", "scale(1, -1)")
      .attr("x", ({ key2 }) => xSubScale(key2))
      .attr("y", -1 * HEIGHT)
      .attr("width", xSubScale.bandwidth())
      .transition()
      .attr("height", ({ value2 }) => HEIGHT - yScale(value2))
      .attr("fill", ({ key2 }) => color(key2));
  }, [data, title, xLabel, yLabel]);

  return (
    <svg ref={svgRef} style={{ margin: '50px', border: '1px solid black' }}>
      <g className='chart' transform={`translate(${margin.left}, ${margin.top})`}>
        <text className='title' />
        <g className="x-axis" />
        <text className='x-label' />
        <g className="y-axis" />
        <text className='y-label' />
        <g className='legend' />
      </g>
    </svg>
  );
}

export default GroupedBarChart;
