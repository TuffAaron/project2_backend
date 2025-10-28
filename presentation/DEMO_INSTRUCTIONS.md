# 🎯 PRESENTATION DEMO INSTRUCTIONS

## Quick Start - Andrew Brown's Work Demo

### Option 1: Live Heroku Demo (RECOMMENDED - NO SETUP!)
```powershell
# Just double-click this file:
presentation\demo-brown-heroku.bat
```

**What it shows:**
- ✅ Live backend on Heroku (no local server needed)
- ✅ OAuth2 login pages in browser
- ✅ REST API endpoints working live
- ✅ Test suite overview
- ✅ All 7 documentation files
- ✅ Git contribution summary (52 commits)
- ✅ Visual HTML presentation

**Time:** 5-10 minutes  
**Requirements:** Just a browser!

---

## Option 2: Full Interactive Demo

### Step 1: Visual HTML Presentation
```powershell
# Open the animated presentation
start presentation\presentation.html
```
- Spacebar or arrow keys to navigate
- Shows all team member contributions
- Professional slide format

### Step 2: Quick Text Demo
```powershell
# Run the quick demo
presentation\quick-presentation.bat
```
- Shows code snippets
- Test results
- Git stats

### Step 3: Detailed Code Showcase
```powershell
# Show actual implementations
presentation\showcase-brown-doge.bat
```
- OAuth2 configuration code
- JWT implementation
- Test examples
- Service implementations

---

## Option 3: E2E Full Stack Demo

### Prerequisites:
1. Frontend repo cloned in parallel folder
2. Node.js installed
3. Git installed

### Run E2E Demo:
```powershell
# Full stack demo with frontend
presentation\e2e-demo-full.bat
```

**This will:**
1. Test backend locally
2. Clone frontend (if needed)
3. Configure frontend connection
4. Start both servers
5. Test end-to-end flow

---

## Option 4: Custom Demo Sequence

### For Technical Audience:

1. **Start with Tests:**
   ```powershell
   # Run test suite
   .\gradlew.bat test
   
   # Open report
   start build\reports\tests\test\index.html
   ```

2. **Show Live Backend:**
   - Open: https://jump-ball-df460ee69b61.herokuapp.com/api/status/health
   - Open: https://jump-ball-df460ee69b61.herokuapp.com/api/games/all
   - Open: https://jump-ball-df460ee69b61.herokuapp.com/api/teams/all

3. **Demo OAuth2:**
   ```powershell
   # Open login page
   start src\main\resources\templates\login.html
   ```

4. **Show Documentation:**
   ```powershell
   # Open all docs
   start docs\OAUTH2_SETUP.md
   start docs\DATABASE_SETUP.md
   start docs\DEPLOY_TO_HEROKU.md
   ```

### For Non-Technical Audience:

1. **Visual Presentation First:**
   ```powershell
   start presentation\presentation.html
   ```

2. **Live Backend Demo:**
   ```powershell
   presentation\demo-brown-heroku.bat
   ```
   (Just watch - it opens everything automatically)

3. **Show Features:**
   - OAuth2 login pages (Google/GitHub buttons)
   - Live API data in browser
   - Professional documentation

---

## 📋 Pre-Demo Checklist

### Before Your Presentation:

- [ ] Verify Heroku backend is up:
  ```powershell
  Invoke-RestMethod https://jump-ball-df460ee69b61.herokuapp.com/api/status/health
  ```

- [ ] Tests are passing:
  ```powershell
  .\gradlew.bat test --console=plain
  ```

- [ ] All presentation files exist:
  ```powershell
  dir presentation\
  ```

- [ ] Browser is ready (close extra tabs)

- [ ] Internet connection is stable

---

## 🎬 Recommended Demo Flow (10 minutes)

### Minute 0-2: Introduction
```powershell
start presentation\presentation.html
```
- Navigate to Andrew Brown's slide
- Show contribution stats

### Minute 2-5: Live Demo
```powershell
presentation\demo-brown-heroku.bat
```
- Let it run through automatically
- Pause at interesting points
- Show OAuth2 pages
- Show live API endpoints

### Minute 5-7: Code Deep Dive (Optional)
```powershell
presentation\showcase-brown-doge.bat
```
- Show actual OAuth2 code
- Show test examples
- Answer technical questions

### Minute 7-10: Q&A
- Have documentation ready:
  ```powershell
  start docs\OAUTH2_SETUP.md
  ```
- Show test report if asked:
  ```powershell
  start build\reports\tests\test\index.html
  ```

---

## 🚨 Troubleshooting

### If Heroku is down:
```powershell
# Check status
heroku apps:info --app jump-ball

# View logs
heroku logs --tail --app jump-ball
```

### If tests fail:
```powershell
# Clean and rebuild
.\gradlew.bat clean test --console=plain
```

### If browser doesn't open:
Manually navigate to:
- Health: https://jump-ball-df460ee69b61.herokuapp.com/api/status/health
- Games: https://jump-ball-df460ee69b61.herokuapp.com/api/games/all
- Teams: https://jump-ball-df460ee69b61.herokuapp.com/api/teams/all

---

## 📱 Mobile/Frontend Demo

### To show frontend connection:

1. **Configure frontend:**
   ```powershell
   # Follow this guide
   start presentation\HEROKU_FRONTEND_SETUP.md
   ```

2. **Create .env in frontend:**
   ```
   EXPO_PUBLIC_API_URL=https://jump-ball-df460ee69b61.herokuapp.com
   ```

3. **Start frontend:**
   ```powershell
   cd ..\CST438_Sports_betting_group14
   npm install
   npx expo start
   ```

4. **Test on phone:**
   - Scan QR code with Expo Go app
   - Show login working
   - Show data loading from Heroku

---

## 🎯 Key Talking Points

### Andrew Brown's Contributions:

1. **Authentication System (OAuth2 + JWT)**
   - Google and GitHub OAuth2 integration
   - JWT tokens for mobile app
   - Secure session management

2. **Test Coverage (~40%)**
   - 55+ comprehensive tests
   - Unit tests with JUnit 5
   - Integration tests with Spock
   - ~800 lines of test code

3. **Documentation (7 Files)**
   - OAuth2 setup guide
   - Database configuration
   - Heroku deployment
   - Postman testing guide

4. **Production Deployment**
   - Live on Heroku
   - JawsDB MySQL database
   - CORS configuration for mobile
   - Multi-environment profiles

5. **Team Leadership**
   - 52 commits (most active)
   - Code reviews and mentoring
   - Architecture decisions

---

## 💡 Pro Tips

1. **Have backup tabs ready** with Heroku URLs already open
2. **Practice the flow once** before presenting
3. **Know where docs are** for quick reference
4. **Test internet** before presenting
5. **Close unnecessary programs** to avoid distractions
6. **Have Postman collection ready** for API demos
7. **Screenshot test results** as backup if tests are slow

---

## 🔗 Quick Links

- **Live Backend:** https://jump-ball-df460ee69b61.herokuapp.com
- **Health Check:** https://jump-ball-df460ee69b61.herokuapp.com/api/status/health
- **Games API:** https://jump-ball-df460ee69b61.herokuapp.com/api/games/all
- **Teams API:** https://jump-ball-df460ee69b61.herokuapp.com/api/teams/all
- **GitHub Repo:** https://github.com/TuffAaron/project2_backend
- **Frontend Repo:** https://github.com/MicahHeneveld/CST438_Sports_betting_group14

---

## 📞 Emergency Contacts

If something goes wrong during the demo:

1. **Backend issues:** Show pre-run test report from `build/reports/tests/test/index.html`
2. **Code questions:** Open `showcase-brown-doge.bat` for quick code access
3. **Documentation:** All in `docs/` folder
4. **Visual fallback:** Use `presentation.html` slides

---

**Ready to present?** Start with:
```powershell
presentation\demo-brown-heroku.bat
```

**Good luck! 🚀**
