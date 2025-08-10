// question-ui-gw/src/utils/token.js
import { parse } from 'date-fns';

const TOKEN_KEY = 'Admin-Token';
const TOKEN_EXP_KEY = 'Admin-Token-Exp';

export const setToken = (token) => {
  localStorage.setItem(TOKEN_KEY, token);
  // 解析token中的过期时间（假设token是JWT格式）
  const exp = parseJwt(token)?.exp;
  if (exp) {
    localStorage.setItem(TOKEN_EXP_KEY, exp * 1000); // 转换为毫秒
  }
};

export const getToken = () => localStorage.getItem(TOKEN_KEY);

export const removeToken = () => {
  localStorage.removeItem(TOKEN_KEY);
  localStorage.removeItem(TOKEN_EXP_KEY);
};

export const isTokenExpired = () => {
  const exp = localStorage.getItem(TOKEN_EXP_KEY);
  return !exp || Date.now() > Number(exp);
};

export const parseJwt = (token) => {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '_').replace(/_/g, '/');
    return JSON.parse(atob(base64));
  } catch (e) {
    return null;
  }
};
