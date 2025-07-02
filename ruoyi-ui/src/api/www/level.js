import request from '@/utils/userRequest'

// 查询关卡列表
export function getLevelDataList(query) {
  return request({
    url: '/www/level/getLevelDataList',
    method: 'get',
    params: query
  })
}

// 查询关卡详细
export function getLevel(id) {
  return request({
    url: '/project/level/' + id,
    method: 'get'
  })
}

