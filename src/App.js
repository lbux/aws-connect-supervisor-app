import React from 'react';
import { AppBar, Toolbar, Typography, Box, Grid, Card, CardContent, Button, List, ListItem } from '@mui/material';

function App() {
  const listItems = Array.from({ length: 5 }, (_, index) => ({
    id: index,
    title: `Recommendation ${index}: Click to view details.`,
    action: index % 2 === 0 ? 'Agent' : 'Queue',
  }));

  return (
    <div>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Home Page
          </Typography>
        </Toolbar>
      </AppBar>
      <Box sx={{ flexGrow: 1, p: 2 }}>
        <Grid container spacing={2}>
          <Grid item xs={12} md={6}>
            <List>
              {listItems.map((item) => (
                <ListItem key={item.id} disablePadding>
                  <Card sx={{ width: '100%' }}>
                    <CardContent>
                      <Typography variant="body1">{item.title}</Typography>
                    </CardContent>
                    <Box display="flex" justifyContent="flex-end" p={2}>
                      <Button variant="contained" color="primary">
                        {item.action}
                      </Button>
                    </Box>
                  </Card>
                </ListItem>
              ))}
            </List>
          </Grid>
          <Grid item xs={12} md={6}>
            <Card>
              <CardContent>
                <Typography variant="h6">Recommendation Details</Typography>
              </CardContent>
            </Card>
            <Card sx={{ mt: 2 }}>
              <CardContent>
                <Typography variant="h6">Recommendation Charts</Typography>
              </CardContent>
            </Card>
          </Grid>
        </Grid>
      </Box>
    </div>
  );
}

export default App;
