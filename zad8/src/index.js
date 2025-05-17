import express from 'express';
import dotenv from 'dotenv';
import cors from 'cors';
import sequelize from './database.js';
import authRoutes from './routes/auth.js';

dotenv.config();
const app = express();
app.use(express.json());

app.use(cors({
  origin: 'http://localhost:5173',
  credentials: true
}));

app.use('/auth', authRoutes);

const PORT = process.env.PORT || 4000;
sequelize.sync().then(() => {
  console.log('Database synced');
  app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
});
