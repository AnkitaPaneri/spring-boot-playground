/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  darkMode: 'media',
  theme: {
    extend: {
      colors: {
        primary: {
          50: '#e6f0f9',
          100: '#cce0f3',
          200: '#99c2e6',
          300: '#66a3da',
          400: '#3385cd',
          500: '#0070c0',
          600: '#005a9a',
          700: '#004373',
          800: '#002d4d',
          900: '#001626',
        },
        secondary: {
          50: '#e6f7ef',
          100: '#ccefdf',
          200: '#99dfbf',
          300: '#66cf9f',
          400: '#33bf7f',
          500: '#00b050',
          600: '#008d40',
          700: '#006a30',
          800: '#004620',
          900: '#002310',
        },
        accent: {
          50: '#fff9e6',
          100: '#fff3cc',
          200: '#ffe799',
          300: '#ffdb66',
          400: '#ffcf33',
          500: '#ffc000',
          600: '#cc9a00',
          700: '#997300',
          800: '#664d00',
          900: '#332600',
        },
      },
      fontFamily: {
        sans: ['Inter', 'sans-serif'],
      },
      boxShadow: {
        card: '0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06)',
      },
    },
  },
  plugins: [],
};
