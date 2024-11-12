const express = require('express');
const bodyParser = require('body-parser');
const sqlite3 = require('sqlite3').verbose();
const path = require('path');

const app = express();
const PORT = 3000;

// Body parser 설정
app.use(bodyParser.json());

// SQLite 데이터베이스 연결
const dbPath = path.resolve(__dirname, '식단관리.db'); // DB 파일 경로
const db = new sqlite3.Database(dbPath, (err) => {
    if (err) {
        console.error('데이터 베이스 오류 :', err.message);
    } else {
        console.log('데이터 베이스 정상 가동');
    }
});

// 로그인
app.post('/login', (req, res) => {
    const { id, pw } = req.body;
    const query = 'select * from User where id = ? and pw = ?';

    db.get(query, [id, pw], (err, row) => {
        if (err) {
            return res.status(500).json({ error: err.message });
        }
        if (row) {
            res.json({ success: true, user: row });
        } else {
            res.status(401).json({ success: false });
        }
    });
});

// 회원가입 id 중복 체크
app.post('/register_C_id', (req, res) => {
    const { id } = req.body;
    const query = 'select * from User where id = ?';

    db.get(query, [id], (err, row) => {
        if (err) {
            return res.status(500).json({ error: err.message });
        }
        if (row) {
            // id가 이미 존재하는 경우
            res.json({ success: false });
        } else {
            // id가 존재하지 않은 경우
            res.json({ success: true });
        }
    });
});

// 회원가입
app.post('/register', (req, res) => {
    const { id, pw, name } = req.body;
    const query = 'insert into User (id, pw, name) values (?, ?, ?)';

    db.run(query, [id, pw, name], function(err) {
        if (err) {
            return res.status(500).json({ error: err.message });
        }
        // 성공적으로 등록된 경우
        res.json({ success: true });
    });
});

// 음식 이름으로 검색
app.get('/select_food_name', (req, res) => {
    const { food_name } = req.query;
    const query = 'select * from Food_N where name like ?';
    
    db.get(query, [`%${food_name}%`], (err, row) => {
        if (err) {
            return res.status(500).json({ error: err.message });
        }
        if (row) {
            res.json({
                success: true,
                food: {
                    food_name: row.Name,
                    food_calorie: row.Calorie,
                    food_carbohydrate: row.Carbohydrate,
                    food_protein: row.Protein,
                    food_fat: row.Fat,
                    food_sugar: row.Sugar,
                    food_sodium: row.Sodium,
                    food_id: row.id
                }
            });
        } else {
            res.status(404).json({ success: false });
        }
    });
});

// 음식 id으로 검색
app.get('/select_food_id', (req, res) => {
    const { food_id } = req.query;
    const query = 'select * from Food_N where id=?';
    
    db.get(query, [food_id], (err, row) => {
        if (err) {
            return res.status(500).json({ error: err.message });
        }
        if (row) {
            res.json({
                success: true,
                food: {
                    food_name: row.Name,
                    food_calorie: row.Calorie,
                    food_carbohydrate: row.Carbohydrate,
                    food_protein: row.Protein,
                    food_fat: row.Fat,
                    food_sugar: row.Sugar,
                    food_sodium: row.Sodium,
                    food_id: row.id
                }
            });
        } else {
            res.status(404).json({ success: false });
        }
    });
});

// 음식 리스트 뽑기
app.get('/select_food_list', (req, res) => {
    const { food_name } = req.query;
    const query = 'select * from Food_N where name like ?';

    db.all(query, [`%${food_name}%`], (err, rows) => {
        if (err) {
            return res.status(500).json({ error: err.message });
        }
        
        if (rows.length > 0) {
            // 결과가 있는 경우, JSON 배열로 반환
            const result = rows.map(row => ({
                food_name: row.Name,
                food_calorie: row.Calorie,
                food_carbohydrate: row.Carbohydrate,
                food_protein: row.Protein,
                food_fat: row.Fat,
                food_sugar: row.Sugar,
                food_sodium: row.Sodium,
                food_id: row.id
            }));
            res.json({ success: true, foods: result });
        } else {
            // 결과가 없는 경우
            res.status(404).json({ success: false });
        }
    });
});

// 사용자의 식단 관리 테이블에 음식 추가
app.post('/insert_food_member', (req, res) => {
    const { user_id, year, month, week, type, foodid } = req.body;
    
    const query = 'insert into Food_M (user_id, user_year, user_month, user_week, type, foodid) values (?, ?, ?, ?, ?, ?)';

    db.run(query, [user_id, year, month, week, type, foodid], function(err) {
        if (err) {
            return res.status(500).json({ success: false, error: err.message });
        }
        
        res.json({ success: true, rowId: this.lastID });
    });
});

// 사용자의 식단 관리 테이블을 불러오기(날짜 사용)
app.get('/select_food_member_list', (req, res) => {
    const { user_id, year, month, week } = req.query;
    
    const query = 'select * from Food_M where user_id = ? AND user_year = ? AND user_month = ? AND user_week = ?';

    db.all(query, [user_id, year, month, week], (err, rows) => {
        if (err) {
            return res.status(500).json({ success: false, error: err.message });
        }
        
        if (rows.length > 0) {
            // 결과가 있을 때 JSON 배열로 반환
            const result = rows.map(row => ({
                user_id: row.user_id,
                user_year: row.user_year,
                user_month: row.user_month,
                user_week: row.user_week,
                type: row.type,
                foodid: row.foodid,
                table_id: row.table_id
            }));
            res.json({ success: true, foodMembers: result });
        } else {
            // 결과가 없는 경우
            res.status(404).json({ success: false });
        }
    });
});

// 사용자 식단 관리 테이블 삭제
app.delete('/deleteFoodmember', (req, res) => {
    const { table_id } = req.body;
    
    const query = 'delete from Food_M where table_id = ?';

    db.run(query, [table_id], function(err) {
        if (err) {
            console.error("Database error:", err.message);
            return res.status(500).json({ success: false, error: err.message });
        }

        // 삭제가 성공적으로 완료되면 성공 메시지 반환
        res.json({ success: true, message: `Record with table_id ${table_id} deleted successfully.` });
    });
});

// 서버 실행
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});