export function listSection(query) {
  return request({
    url: '/section/section/list',
    method: 'get',
    params: query
  })
}