try {
  // Connect to the admin database first
  db = db.getSiblingDB('admin');
  
  print('Connecting to MongoDB as admin...');
  // Authenticate with admin credentials
  db.auth('mongoadmin', 'password');
  
  print('Switching to monstersdb...');
  // Then switch to the target database
  db = db.getSiblingDB('monstersdb');
  
  // Check if collection already exists to avoid duplicate inserts
  const collections = db.getCollectionNames();
  print('Existing collections: ' + JSON.stringify(collections));
  
  // Import the monsters data from the JSON file using fs module
  print('Reading monsters JSON file...');
  const fs = require('fs');
  const monstersData = JSON.parse(fs.readFileSync('/monsters.json', 'utf8'));
  print('Found ' + monstersData.length + ' monsters to import');
  
  print('Inserting monsters into collection...');
  // Clear existing collection if it exists
  db.monsters.drop();
  
  // Insert the monsters into the collection
  const result = db.monsters.insertMany(monstersData);
  print('Monsters data import result: ' + JSON.stringify(result));
  
  // Also try with capital M in case Spring is using the exact class name
  db.Monsters.drop();
  db.Monsters.insertMany(monstersData);
  
  // List all collections after insert to verify
  print('Collections after import: ' + JSON.stringify(db.getCollectionNames()));
  
  // Count documents to verify
  print('Number of documents in monsters collection: ' + db.monsters.count());
  print('Number of documents in Monsters collection: ' + db.Monsters.count());
  
} catch (e) {
  print('Error during monster data import: ' + e);
  // Print stack trace for more detailed debugging
  print(e.stack);
}
