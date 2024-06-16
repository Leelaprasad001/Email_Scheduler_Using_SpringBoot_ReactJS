const prod = {
  url: {
   API_BASE_URL: 'http://localhost:8088/emailsender-api-0.0.1-SNAPSHOT',
   
  }
}

const dev = {
  url: {
    API_BASE_URL: 'http://localhost:8088/emailsender-api-0.0.1-SNAPSHOT'
  }
}

export const config = process.env.NODE_ENV === 'development' ? dev : prod