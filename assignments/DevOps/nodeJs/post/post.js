const express = require('express');
const app = express();
const axios = require('axios');

app.use(express.json());

app.post('/notify', (req, res) => {
    const { userId, title, content } = req.body;

    // User 서비스로부터 사용자 정보 조회
    axios.get(`http://user-service:3001/users/${userId}`)
        .then(response => {
            const user = response.data;
            console.log(`Sending notification to ${user.email}: New post titled "${title}"`);
            res.json({ message: "Notification sent" });
        })
        .catch(err => {
            res.status(500).json({ message: "Error sending notification" });
        });
});

app.listen(3003, () => {
    console.log('Notification service running on port 3003');
});
