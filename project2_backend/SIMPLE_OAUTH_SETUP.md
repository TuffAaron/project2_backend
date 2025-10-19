# 🚀 Simple OAuth2 Setup (5 Minutes!)

## The Easy Way: Just Use ngrok

### Step 1: Install ngrok
- Go to https://ngrok.com/download 
- Download and extract it
- Or use: `choco install ngrok` (Windows) / `brew install ngrok` (Mac)

### Step 2: Setup GitHub OAuth2 (Easiest provider)
1. Go to: https://github.com/settings/developers
2. Click "New OAuth App"
3. Fill in:
   ```
   Application name: My Project OAuth
   Homepage URL: https://abc123.ngrok.io  (you'll get this in Step 4)
   Authorization callback URL: https://abc123.ngrok.io/login/oauth2/code/github
   ```
4. Save and copy your Client ID and Client Secret

### Step 3: Update Your Environment
Just edit your `.env.example` file:
```env
APP_BASE_URL=https://abc123.ngrok.io
GITHUB_CLIENT_ID=your_client_id_here
GITHUB_CLIENT_SECRET=your_client_secret_here
```

### Step 4: Run Everything
```bash
# Terminal 1: Start your Spring Boot app
gradlew bootRun

# Terminal 2: Start ngrok (this gives you the https URL)
ngrok http 8080

# Copy the https URL from ngrok output (like https://abc123.ngrok.io)
# Update your .env.example with this URL
# Update your GitHub OAuth app with this URL
# Restart your Spring Boot app
```

### Step 5: Test
- Open: `https://abc123.ngrok.io/login`
- Click GitHub login
- Should work from any device!

## That's it! 🎉

**Why this works:**
- ngrok gives you HTTPS (required for OAuth2)
- Works from any device/IP
- No complex deployment needed
- Takes 5 minutes

**The URL changes each time you restart ngrok, but for development that's fine!**