import { useState } from 'react';
import TimelineIcon from '@mui/icons-material/Timeline';
import LanguageIcon from '@mui/icons-material/Language';
import LocalHospitalIcon from '@mui/icons-material/LocalHospital';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import Tabs from '@mui/material/Tabs';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';

// import Map from './components/Map';
import BarChart from './components/BarChart';
import GroupedBarChart from './components/GroupedBarChart';
import LineChart from './components/LineChart';
import MultiLineChart from './components/MultiLineChart';

// import mapData from './data/state.geo.json';
import mastodonLanguageData from './data/Language_Mastodon.json';
import tweetCovidLanguageData from './data/language_Covid.json';
import tweetLockdownLanguageData from './data/language_Lockdown.json'
import groupedBarChartCovidData from './data/Tweet_Hospital_Number_Covid.json';
import groupedBarChartLockdownData from './data/Tweet_Hospital_Number_Lockdown.json';
import lineChartData from './data/Time_Line_Mastodon.json';
import multiLineChartData from './data/time_line_Lockdown_Covid.json';
import './App.css';

function TabPanel(props) {
  return (
    <div hidden={props.value !== props.index}>
      <Box sx={{ p: 3 }} style={{ display: 'flex', flexDirection: 'column', justifyContent: 'center' }}>
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
            ? "Scenario 1: Comparing the number of covid and lockdown data released each month"
            : value === 1
            ? "Scenario 2: Comparing the languages ​​other than English of covid and lockdown data"
            : value === 2
            ? "Scenario 3: Comparing the number of covid and lockdown Tweets with the number of hospital"
            : ""}
          </Typography>
        </Toolbar>
      </AppBar>

      <Box sx={{ display: 'flex', minHeight: '90vh' }}>
        {/* SideNav */}
        <Tabs
          orientation="vertical"
          value={value}
          onChange={handleChange}
          sx={{ borderRight: 1, borderColor: 'divider' }}
        >
          <Tab icon={<TimelineIcon />} label={"Timeline"} value={0} />
          <Tab icon={<LanguageIcon />} label={"Langauge"} value={1} />
          <Tab icon={<LocalHospitalIcon />} label={"Hospital"} value={2} />
        </Tabs>

        {/* Content */}
        <TabPanel value={value} index={0}>
          <LineChart data={lineChartData.rows} title={"Number of Mastodon Toots Timeline"} xLabel={"Month"} yLabel={"Count"} />
          <MultiLineChart data={multiLineChartData.rows} title={"Number of Tweets mentioning 'Lockdown' and 'Covid' Timeline"} xLabel={"Month"} yLabel={"Count"} /> 
        </TabPanel>
        <TabPanel value={value} index={1}>
          <BarChart data={mastodonLanguageData.rows} title={'Language used in Mastodon Toots Count'} xLabel={'Language'} yLabel={'Count'} />
          <BarChart data={tweetCovidLanguageData.rows} title={"Language used in Tweets mentioning 'Covid' Count"} xLabel={'Language'} yLabel={'Count'} />
          <BarChart data={tweetLockdownLanguageData.rows} title={"Language used in Tweets mentioning 'Lockdown' Count"} xLabel={'Language'} yLabel={'Count'} />
        </TabPanel>
        <TabPanel value={value} index={2}>
          <GroupedBarChart data={groupedBarChartCovidData.rows} title={"Number of Tweets mentioning 'Covid' and Number of Hospital in each State"} xLabel={'State'} yLabel={'Count'} />
          <GroupedBarChart data={groupedBarChartLockdownData.rows} title={"Number of Tweets mentioning 'Lockdown' and Number of Hospital in each State"} xLabel={'State'} yLabel={'Count'} />
        </TabPanel>
      </Box>
    </div>
  );
}

export default App;
