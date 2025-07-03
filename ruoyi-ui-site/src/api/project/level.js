import request from '@/utils/request'

// 查询关卡列表
export function listLevel(query) {
  return request({
    url: '/project/level/list',
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

// 新增关卡
export function addLevel(data) {
  return request({
    url: '/project/level',
    method: 'post',
    data: data
  })
}

// 修改关卡
export function updateLevel(data) {
  return request({
    url: '/project/level',
    method: 'put',
    data: data
  })
}

// 删除关卡
export function delLevel(id) {
  return request({
    url: '/project/level/' + id,
    method: 'delete'
  })
}
