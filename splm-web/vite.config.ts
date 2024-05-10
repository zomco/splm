import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

const config = {
  plugins: [react()],
  build: {
    outDir: '../splm/src/main/resources/static/',
    emptyOutDir: true,
  },
}

// https://vitejs.dev/config/
export default defineConfig(({ command, mode }) => {
  if (command === 'serve') {
    const isDev = mode === 'development';
    return {
      ...config,
      server: {
        proxy: {
          '/api': {
            target: isDev ? 'http://127.0.0.1:8080' : 'http://127.0.0.1',
            changeOrigin: isDev,
          },
          '/ws': {
            target: isDev ? `ws://127.0.0.1:8080` : 'ws://127.0.0.1',
            changeOrigin: isDev,
            ws: true,
          },
        }
      },
    }
  } else {
    return config
  }
})
