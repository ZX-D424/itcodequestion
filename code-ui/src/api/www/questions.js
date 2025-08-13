import request from '@/utils/userRequest'

// 查询用户答题评分列表
export function listQuestions(query) {
  return request({
    url: '/www/questions/list',
    method: 'get',
    params: query
  })
}
