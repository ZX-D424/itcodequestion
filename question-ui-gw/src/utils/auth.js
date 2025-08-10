// question-ui-gw/src/utils/auth.js
export const TOKEN_KEY = 'Admin-Token';

export const setToken = (token) => localStorage.setItem(TOKEN_KEY, token);

export const getToken = () => localStorage.getItem(TOKEN_KEY);

export const removeToken = () => localStorage.removeItem(TOKEN_KEY);

export const isAuthenticated = () => !!getToken();
