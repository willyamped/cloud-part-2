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
            COMP90024
          </Typography>
        </Toolbar>
      </AppBar>

      <Box sx={{ flexGrow: 1, bgcolor: 'background.paper', display: 'flex', height: '100vh' }}>
        {/* SideNav */}
        <Tabs
          orientation="vertical"
          value={value}
          onChange={handleChange}
          sx={{ borderRight: 1, borderColor: 'divider' }}
        >
          <Tab icon=<CoronavirusIcon /> label="Covid-19" />
          <Tab icon=<EmojiFlagsIcon /> label="Ukraine" />
          <Tab icon=<LanguageIcon /> label="Langauge" />
        </Tabs>

        {/* Content */}
        <TabPanel value={value} index={0}>
          Item One
        </TabPanel>
        <TabPanel value={value} index={1}>
          Item Two
        </TabPanel>
        <TabPanel value={value} index={2}>
          Item Three
        </TabPanel>
      </Box>
    </div>
  );
}

export default App;
