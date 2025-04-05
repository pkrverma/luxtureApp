// backend/server.js
const express = require("express");
const Razorpay = require("razorpay");
const cors = require("cors");
const dotenv = require("dotenv");

dotenv.config();
const app = express();
app.use(cors());
app.use(express.json());

const razorpay = new Razorpay({
  key_id: process.env.RAZORPAY_KEY_ID,
  key_secret: process.env.RAZORPAY_KEY_SECRET,
});

// Create Razorpay order
app.post("/createOrder", async (req, res) => {
  const { amount, currency = "INR" } = req.body;
  try {
    const options = {
      amount: amount * 100,
      currency,
      receipt: "order_rcptid_" + Date.now(),
    };
    const order = await razorpay.orders.create(options);
    res.status(200).json({ success: true, order });
  } catch (error) {
    res.status(500).json({ success: false, message: error.message });
  }
});

app.listen(5000, () => {
  console.log("Razorpay backend running on http://localhost:5000");
});
