import request from '@/utils/request'

// 查询简历指导记录列表
export function listGuidance(query) {
  return request({
    url: '/project/guidance/list',
    method: 'get',
    params: query
  })
}

// 查询简历指导记录详细
export function getGuidance(id) {
  return request({
    url: '/project/guidance/' + id,
    method: 'get'
  })
}

// 新增简历指导记录
export function addGuidance(data) {
  return request({
    url: '/project/guidance',
    method: 'post',
    data: data
  })
}

// 修改简历指导记录
export function updateGuidance(data) {
  return request({
    url: '/project/guidance',
    method: 'put',
    data: data
  })
}

// 删除简历指导记录
export function delGuidance(id) {
  return request({
    url: '/project/guidance/' + id,
    method: 'delete'
  })
}
