import request from '@/utils/userRequest'

// 查询模块排名
export function getModuleRank(moduleId) {
  return request({
    url: '/www/rank/moduleRank/' + moduleId,
    method: 'get'
  })
}

// 查询模块排名
export function getTotalRank() {
  return request({
    url: '/www/rank/totalRank',
    method: 'get'
  })
}

// 获取个人总排名
export function getPersonalTotalRank() {
  return request({
    url: '/www/rank',
    method: 'get'
  })
}

// 获取个人模块排名
export function getPersonalModuleRank(moduleId) {
  return request({
    url: '/www/rank/'+moduleId,
    method: 'get'
  })
}