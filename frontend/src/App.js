import { useState } from 'react';
import CoronavirusIcon from '@mui/icons-material/Coronavirus';
import EmojiFlagsIcon from '@mui/icons-material/EmojiFlags';
import LanguageIcon from '@mui/icons-material/Language';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import Tabs from '@mui/material/Tabs';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Map from './components/Map';
import data from './data/GeoChart.world.geo.json';
import './App.css';

function TabPanel(props) {
  return (
    <div hidden={props.value !== props.index}>
      <Box sx={{ p: 3 }}>
        {props.children}
      </Box>
    </div>
  );
}

function App() {
  const [value, setValue] = useState('pop_est');

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <div>
      {/* NavBar */}
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h5">
            COMP90024
          </Typography>
        </Toolbar>
      </AppBar>

      <Box sx={{ flexGrow: 1, bgcolor: 'background.paper', display: 'flex', height: '90vh' }}>
        {/* SideNav */}
        <Tabs
          orientation="vertical"
          value={value}
          onChange={handleChange}
          sx={{ borderRight: 1, borderColor: 'divider' }}
        >
          <Tab icon=<CoronavirusIcon /> label="Covid-19" value='pop_est' />
          <Tab icon=<EmojiFlagsIcon /> label="Ukraine" value='name_len' />
          <Tab icon=<LanguageIcon /> label="Langauge" value='gdp_md_est' />
        </Tabs>

        {/* Content */}
        {/* <TabPanel value={value} index={0}>
          Item One
        </TabPanel>
        <TabPanel value={value} index={1}>
          Item Two
        </TabPanel>
        <TabPanel value={value} index={2}>
          Item Three
        </TabPanel> */}
        <Map data={data} property={value} />
      </Box>
    </div>
  );
}

export default App;
