import { useState } from "react";

import HomeIcon from "@mui/icons-material/Home";
import TimelineIcon from "@mui/icons-material/Timeline";
import LanguageIcon from "@mui/icons-material/Language";
import LocalHospitalIcon from "@mui/icons-material/LocalHospital";
import PeopleIcon from "@mui/icons-material/People";

import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Tab from "@mui/material/Tab";
import Tabs from "@mui/material/Tabs";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";

import BarChart from "./components/BarChart";
import GroupedBarChartDualYAxes from "./components/GroupedBarChartDualYAxes";
import LineChart from "./components/LineChart";
import MultiLineChart from "./components/MultiLineChart";

import mastodonLanguageData from "./data/Language_Mastodon.json";
import tweetCovidLanguageData from "./data/language_Covid.json";
import tweetLockdownLanguageData from "./data/language_Lockdown.json"
import tweetCovidHospitalData from "./data/Tweet_Hospital_Number_Covid.json";
import tweetLockdownHospitalData from "./data/Tweet_Hospital_Number_Lockdown.json";
import mastodonTimelineData from "./data/Time_Line_Mastodon.json";
import tweetTimelineData from "./data/time_line_Lockdown_Covid.json";
import tweetCovidPopulationData from "./data/S_#TC_P.json";
import tweetLockdownPopulationData from "./data/S_#TL_P.json";
import covidImg from "./data/covid.jpg";

import "./App.css";

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

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <div>
      {/* NavBar */}
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h5">
            {value === 0
            ? "COMP90054 Assignment 2"
            : value === 1
            ? "Scenario 1: Comparing the number of covid and lockdown data released each month"
            : value === 2
            ? "Scenario 2: Comparing the language other than English of covid and lockdown data"
            : value === 3
            ? "Scenario 3: Comparing the number of covid and lockdown Tweets with the number of hospital"
            : value === 4
            ? "Scenario 4: Comparing the number of covid and lockdown Tweets with the population size"
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
          <MultiLineChart data={tweetTimelineData.rows} title={"Number of Tweets mentioning 'Lockdown' and 'Covid' Timeline"} xLabel={"Month"} yLabel={"Count"} /> 
          <LineChart data={mastodonTimelineData.rows} title={"Number of Mastodon Toots Timeline"} xLabel={"Month"} yLabel={"Count"} />
        </TabPanel>
        <TabPanel value={value} index={2}>
          <BarChart data={tweetCovidLanguageData.rows} title={"Language used in Tweets mentioning 'Covid' Count"} xLabel={"Language"} yLabel={"Count"} />
          <BarChart data={tweetLockdownLanguageData.rows} title={"Language used in Tweets mentioning 'Lockdown' Count"} xLabel={"Language"} yLabel={"Count"} />
          <BarChart data={mastodonLanguageData.rows} title={"Language used in Mastodon Toots Count"} xLabel={"Language"} yLabel={"Count"} />
        </TabPanel>
        <TabPanel value={value} index={3}>
          <GroupedBarChartDualYAxes data={tweetCovidHospitalData.rows} title={"Number of Tweets mentioning 'Covid' and Number of Hospital in each State"} xLabel={"State"} yLeftLabel={"Tweet Count"} yRightLabel={"Hospital Count"} />
          <GroupedBarChartDualYAxes data={tweetLockdownHospitalData.rows} title={"Number of Tweets mentioning 'Lockdown' and Number of Hospital in each State"} xLabel={"State"} yLeftLabel={"Tweet Count"} yRightLabel={"Hospital Count"} />
        </TabPanel>
        <TabPanel value={value} index={4}>
          <GroupedBarChartDualYAxes data={tweetCovidPopulationData} title={"Number of Tweets mentioning 'Covid' and Population Size in each State"} xLabel={"State"} yLabel={"Count"} />
          <GroupedBarChartDualYAxes data={tweetLockdownPopulationData} title={"Number of Tweets mentioning 'Lockdown' and Population Size in each State"} xLabel={"State"} yLabel={"Count"} />
        </TabPanel>
      </Box>
    </div>
  );
}

export default App;
