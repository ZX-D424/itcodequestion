import request from '@/utils/request'

// 查询得分排名列表
export function listRank(query) {
  return request({
    url: '/project/rank/list',
    method: 'get',
    params: query
  })
}

// 查询得分排名详细
export function getRank(id) {
  return request({
    url: '/project/rank/' + id,
    method: 'get'
  })
}

// 新增得分排名
export function addRank(data) {
  return request({
    url: '/project/rank',
    method: 'post',
    data: data
  })
}

// 修改得分排名
export function updateRank(data) {
  return request({
    url: '/project/rank',
    method: 'put',
    data: data
  })
}

// 删除得分排名
export function delRank(id) {
  return request({
    url: '/project/rank/' + id,
    method: 'delete'
  })
}
