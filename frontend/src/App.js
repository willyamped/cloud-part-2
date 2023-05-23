import axios from "axios";
import { useEffect, useState } from "react";

import HomeIcon from "@mui/icons-material/Home";
import TimelineIcon from "@mui/icons-material/Timeline";
import LanguageIcon from "@mui/icons-material/Language";
import LocalHospitalIcon from "@mui/icons-material/LocalHospital";
import PeopleIcon from "@mui/icons-material/People";
import PieChartIcon from '@mui/icons-material/PieChart';

import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Tab from "@mui/material/Tab";
import Tabs from "@mui/material/Tabs";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";

import BarChart from "./components/BarChart";
import GroupedBarChartDualYAxes from "./components/GroupedBarChartDualYAxes";
import MultiLineChart from "./components/MultiLineChart";
// import PieChart from './components/PieChart';

import mastodonLanguageData from "./data/Language_Mastodon.json";
import tweetProportionData from "./data/count.json";
import covidImg from "./data/covid.jpg";

import "./App.css";
import MultiLineChartDualYAxes from './components/MultiLineChartDualYAxes';

const populationUrl = "http://172.26.134.11/sudo-regional-population/_design/populaiton/_view/polulation?reduce=true&group=true";
const hospitalUrl = "http://172.26.134.11/sudo-hospital-beds/_design/Hospital/_view/hospital%20number?reduce=true&group=true";
const tweetCovidTimelineUrl = "http://172.26.134.11/tweets-covid/_design/covid/_view/Timeline?reduce=true&group=true";
const tweetCovidStateUrl = "http://172.26.134.11/tweets-covid/_design/covid/_view/Location?reduce=true&group=true";
const tweetCovidLanguageUrl = "http://172.26.134.11/tweets-covid/_design/covid/_view/language?reduce=true&group=true";
const tweetLockdownTimelineUrl = "http://172.26.134.11/tweets-lockdown/_design/lockdown/_view/Timeline?reduce=true&group=true";
const tweetLockdownStateUrl = "http://172.26.134.11/tweets-lockdown/_design/lockdown/_view/Location?reduce=true&group=true";
const tweetLockdownLanguageUrl = "http://172.26.134.11/tweets-lockdown/_design/lockdown/_view/language?reduce=true&group=true";

function TabPanel(props) {
  return (
    <div hidden={props.value !== props.index}>
      <Box sx={{ p: 3 }} style={{ display: "flex", flexDirection: "column", justifyContent: "center" }}>
        {props.children}
      </Box>
    </div>
  );
}

function App() {
  const [value, setValue] = useState(0);

  const [hospitalData, setHospitalData] = useState([]);
  const [populationData, setPopulationData] = useState([]);
  const [tweetCovidTimelineData, setTweetCovidTimelineData] = useState([]);
  const [tweetCovidStateData, setTweetCovidStateData] = useState([]);
  const [tweetCovidLanguageData, setTweetCovidLanguageData] = useState([]);
  const [tweetLockdownTimelineData, setTweetLockdownTimelineData] = useState([]);
  const [tweetLockdownStateData, setTweetLockdownStateData] = useState([]);
  const [tweetLockdownLanguageData, setTweetLockdownLanguageData] = useState([]);

  const [tweetTimelineData, setTweetTimelineData] = useState([]);
  const [tweetCovidHospitalData, setTweetCovidHospitalData] = useState([]);
  const [tweetCovidHospitalData2, setTweetCovidHospitalData2] = useState([]);
  const [tweetLockdownHospitalData, setTweetLockdownHospitalData] = useState([]);
  const [tweetLockdownHospitalData2, setTweetLockdownHospitalData2] = useState([]);
  const [tweetCovidPopulationData, setTweetCovidPopulationData] = useState([]);
  const [tweetCovidPopulationData2, setTweetCovidPopulationData2] = useState([]);
  const [tweetLockdownPopulationData, setTweetLockdownPopulationData] = useState([]);
  const [tweetLockdownPopulationData2, setTweetLockdownPopulationData2] = useState([]);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  useEffect(() => {
    axios.get(hospitalUrl).then((response) => {
      setHospitalData(response.data);
    });
    axios.get(populationUrl).then((response) => {
      setPopulationData(response.data);
    });
    axios.get(tweetCovidTimelineUrl).then((response) => {
      setTweetCovidTimelineData(response.data);
    });
    axios.get(tweetCovidStateUrl).then((response) => {
      setTweetCovidStateData(response.data);
    });
    axios.get(tweetCovidLanguageUrl).then((response) => {
      setTweetCovidLanguageData(response.data.rows.filter(({ key }) => key !== "en"));
    });
    axios.get(tweetLockdownTimelineUrl).then((response) => {
      setTweetLockdownTimelineData(response.data);
    });
    axios.get(tweetLockdownStateUrl).then((response) => {
      setTweetLockdownStateData(response.data);
    });
    axios.get(tweetLockdownLanguageUrl).then((response) => {
      setTweetLockdownLanguageData(response.data.rows.filter(({ key }) => key !== "en"));
    });
  }, [])

  useEffect(() => {
    if (tweetCovidTimelineData?.rows && tweetLockdownTimelineData?.rows) {
      setTweetTimelineData([
        { 
          "key": "Covid",
          "value": tweetCovidTimelineData.rows 
        },
        { 
          "key": "Lockdown",
          "value": tweetLockdownTimelineData.rows
        }
      ])
    }
  }, [tweetCovidTimelineData, tweetLockdownTimelineData])
  useEffect(() => {
    if (hospitalData?.rows && tweetCovidStateData?.rows) {
      const states = hospitalData.rows.map(({ key }) => key)
      
      // multi line chart
      const reducedTweetCovidStateData = []
      for (let i = 0; i < states.length; i++) {
        reducedTweetCovidStateData.push({
          "key": states[i],
          "value": tweetCovidStateData.rows.filter(({ key }) => key === states[i])[0]['value']
        })
      }
      setTweetCovidHospitalData([
        { 
          "key": "Tweet",
          "value": reducedTweetCovidStateData
        },
        { 
          "key": "Hospital",
          "value": hospitalData.rows 
        }
      ])

      // grouped bar chart
      const temp = []
      for (let i = 0; i < states.length; i++) {
        temp.push({
          "key": states[i],
          "value": {
            "Tweet": tweetCovidStateData.rows.filter(({ key }) => key === states[i])[0]['value'],
            "Hospital": hospitalData.rows.filter(({ key }) => key === states[i])[0]['value']
          }
        })
      }
      setTweetCovidHospitalData2(temp)
    }
  }, [hospitalData, tweetCovidStateData])
  useEffect(() => {
    if (hospitalData?.rows && tweetLockdownStateData?.rows) {
      const states = hospitalData.rows.map(({ key }) => key)
      
      // multi line chart
      const reducedTweetLockdownStateData = []
      for (let i = 0; i < states.length; i++) {
        reducedTweetLockdownStateData.push({
          "key": states[i],
          "value": tweetLockdownStateData.rows.filter(({ key }) => key === states[i])[0]['value']
        })
      }
      setTweetLockdownHospitalData([
        { 
          "key": "Tweet",
          "value": reducedTweetLockdownStateData
        },
        { 
          "key": "Hospital",
          "value": hospitalData.rows 
        }
      ])

      // grouped bar chart
      const temp = []
      for (let i = 0; i < states.length; i++) {
        temp.push({
          "key": states[i],
          "value": {
            "Tweet": tweetLockdownStateData.rows.filter(({ key }) => key === states[i])[0]['value'],
            "Hospital": hospitalData.rows.filter(({ key }) => key === states[i])[0]['value']
          }
        })
      }
      setTweetLockdownHospitalData2(temp)
    }
  }, [hospitalData, tweetLockdownStateData])
  useEffect(() => {
    if (populationData?.rows && tweetCovidStateData?.rows) {
      const states = tweetCovidStateData.rows.map(({ key }) => key)

      // multi line chart
      let reducedPopulationData = []
      for (let i = 0; i < states.length; i++) {
        reducedPopulationData.push({
          "key": states[i],
          "value": populationData.rows.filter(({ key }) => key === states[i])[0]['value']
        })
      }
      setTweetCovidPopulationData([
        { 
          "key": "Tweet",
          "value": tweetCovidStateData.rows
        },
        { 
          "key": "Population",
          "value": reducedPopulationData
        }
      ])

      // grouped bar chart
      const temp = []
      for (let i = 0; i < states.length; i++) {
        temp.push({
          "key": states[i],
          "value": {
            "Tweet": tweetCovidStateData.rows.filter(({ key }) => key === states[i])[0]['value'],
            "Population": populationData.rows.filter(({ key }) => key === states[i])[0]['value']
          }
        })
      }
      setTweetCovidPopulationData2(temp)
    }
  }, [populationData, tweetCovidStateData])
  useEffect(() => {
    if (populationData?.rows && tweetLockdownStateData?.rows) {
      const states = tweetLockdownStateData.rows.map(({ key }) => key)

      // multi line chart
      let reducedPopulationData = []
      for (let i = 0; i < states.length; i++) {
        reducedPopulationData.push({
          "key": states[i],
          "value": populationData.rows.filter(({ key }) => key === states[i])[0]['value']
        })
      }
      setTweetLockdownPopulationData([
        { 
          "key": "Tweet",
          "value": tweetLockdownStateData.rows
        },
        { 
          "key": "Population",
          "value": reducedPopulationData
        }
      ])

      // grouped bar chart
      const temp = []
      for (let i = 0; i < states.length; i++) {
        temp.push({
          "key": states[i],
          "value": {
            "Tweet": tweetLockdownStateData.rows.filter(({ key }) => key === states[i])[0]['value'],
            "Population": populationData.rows.filter(({ key }) => key === states[i])[0]['value']
          }
        })
      }
      setTweetLockdownPopulationData2(temp)
    }
  }, [populationData, tweetLockdownStateData])
  

  return (
    <div>
      {/* NavBar */}
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h5">
            {value === 0
            ? "COMP90024 Assignment 2"
            : value === 1
            ? "Scenario 1: Comparing the number of covid and lockdown data released each month"
            : value === 2
            ? "Scenario 2: Comparing the language other than English of covid and lockdown data"
            : value === 3
            ? "Scenario 3: Comparing the number of covid and lockdown Tweets with the number of hospital"
            : value === 4
            ? "Scenario 4: Comparing the number of covid and lockdown Tweets with the population size"
            : value === 5
            ? "Scenario 5: Comparing the proportion of coivd and lockdown Tweets"
            : ""}
          </Typography>
        </Toolbar>
      </AppBar>

      <Box sx={{ display: "flex", minHeight: "90vh" }}>
        {/* SideNav */}
        <Tabs
          orientation="vertical"
          value={value}
          onChange={handleChange}
          sx={{ borderRight: 1, borderColor: "divider" }}
        >
          <Tab icon={<HomeIcon />} label={"Home"} value={0} />
          <Tab icon={<TimelineIcon />} label={"Timeline"} value={1} />
          <Tab icon={<LanguageIcon />} label={"Language"} value={2} />
          <Tab icon={<LocalHospitalIcon />} label={"Hospital"} value={3} />
          <Tab icon={<PeopleIcon />} label={"Population"} value={4} />
          <Tab icon={<PieChartIcon />} label={"Proportion"} value={5} />
        </Tabs>

        {/* Content */}
        <TabPanel value={value} index={0}>
          <Box style={{ display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center", width: "80vw", minHeight: "70vh" }}>
            <img src={covidImg} alt="covid" height={200} />
            <br />
            <Typography variant='h4' align="center">
              The Covid-19 pandemic
            </Typography>
            <Typography variant='h6' align="center">
              has tremendously changed the life of people around the globe
            </Typography>
            <br />
            <Typography variant='h5' align="center" sx={{ fontStyle: 'italic' }}>
              What about the people of Australia?
            </Typography>
          </Box>
        </TabPanel>
        <TabPanel value={value} index={1}>
          {/* <PieChart data={tweetProportionData} title={"Proportion of Tweets mentioning 'Covid' and 'Lockdown'"} /> */}
          <MultiLineChart data={tweetTimelineData} title={"Number of Tweets mentioning 'Lockdown' and 'Covid' Timeline"} xLabel={"Month"} yLabel={"Count"} /> 
        </TabPanel>
        <TabPanel value={value} index={2}>
          <BarChart data={tweetCovidLanguageData} title={"Language used in Tweets mentioning 'Covid' Count"} xLabel={"Language"} yLabel={"Count"} />
          <BarChart data={tweetLockdownLanguageData} title={"Language used in Tweets mentioning 'Lockdown' Count"} xLabel={"Language"} yLabel={"Count"} />
          <BarChart data={mastodonLanguageData.rows} title={"Language used in Mastodon Toots Count"} xLabel={"Language"} yLabel={"Count"} />
        </TabPanel>
        <TabPanel value={value} index={3}>
          <MultiLineChartDualYAxes data={tweetCovidHospitalData} title={"Number of Tweets mentioning 'Covid' and Number of Hospital in each State"} xLabel={"State"} yLeftLabel={"Tweet Count"} yRightLabel={"Hospital Count"} />
          <GroupedBarChartDualYAxes data={tweetCovidHospitalData2} title={"Number of Tweets mentioning 'Covid' and Number of Hospital in each State"} xLabel={"State"} yLeftLabel={"Tweet Count"} yRightLabel={"Hospital Count"} />
          <MultiLineChartDualYAxes data={tweetLockdownHospitalData} title={"Number of Tweets mentioning 'Lockdown' and Number of Hospital in each State"} xLabel={"State"} yLeftLabel={"Tweet Count"} yRightLabel={"Hospital Count"} />
          <GroupedBarChartDualYAxes data={tweetLockdownHospitalData2} title={"Number of Tweets mentioning 'Lockdown' and Number of Hospital in each State"} xLabel={"State"} yLeftLabel={"Tweet Count"} yRightLabel={"Hospital Count"} />
        </TabPanel>
        <TabPanel value={value} index={4}>
          <MultiLineChartDualYAxes data={tweetCovidPopulationData} title={"Number of Tweets mentioning 'Covid' and Population Size in each State"} xLabel={"State"} yLeftLabel={"Tweet Count"} yRightLabel={"Population Size"} />
          <GroupedBarChartDualYAxes data={tweetCovidPopulationData2} title={"Number of Tweets mentioning 'Covid' and Population Size in each State"} xLabel={"State"} yLeftLabel={"Tweet Count"} yRightLabel={"Population Size"} />
          <MultiLineChartDualYAxes data={tweetLockdownPopulationData} title={"Number of Tweets mentioning 'Lockdown' and Population Size in each State"} xLabel={"State"} yLeftLabel={"Tweet Count"} yRightLabel={"Population Size"} />
          <GroupedBarChartDualYAxes data={tweetLockdownPopulationData2} title={"Number of Tweets mentioning 'Lockdown' and Population Size in each State"} xLabel={"State"} yLeftLabel={"Tweet Count"} yRightLabel={"Population Size"} />
        </TabPanel>
        <TabPanel value={value} index={5}>
          <BarChart data={tweetProportionData.rows} title={"Proportion of Tweets mentioning 'Covid' and 'Lockdown'"} />
        </TabPanel>
      </Box>
    </div>
  );
}

export default App;
