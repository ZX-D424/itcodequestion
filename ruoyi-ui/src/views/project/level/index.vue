<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所属模块" prop="moduleId">
        <el-select v-model="queryParams.moduleId" placeholder="请选择所属模块" style="width: 200px;" >
          <el-option
              v-for="module in moduleDataList"
              :key="module.id"
              :label="module.name"
              :value="module.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关卡编码" prop="levelCode">
        <el-input
          v-model="queryParams.levelCode"
          placeholder="请输入关卡编码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关卡标题" prop="levelTitle">
        <el-input
          v-model="queryParams.levelTitle"
          placeholder="请输入关卡标题"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['project:level:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['project:level:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['project:level:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['project:level:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="levelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="所属模块" align="center" prop="moduleName" />
      <el-table-column label="关卡序号" align="center" prop="levelCode" />
      <el-table-column label="关卡标题" align="center" prop="levelTitle" />
      <el-table-column label="关卡信息" align="center" prop="levelInfo" show-overflow-tooltip />
      <el-table-column label="关卡类型" align="center" prop="levelType">
        <template #default="scope">
          <dict-tag :options="level_type" :value="scope.row.levelType"/>
        </template>
      </el-table-column>
      <el-table-column label="关卡图片" align="center" prop="imgUrl" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.imgUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['project:level:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['project:level:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改关卡对话框 -->
    <el-dialog :title="title" v-model="open" width="60%" append-to-body>
      <el-form ref="levelRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属模块" prop="moduleId">
          <el-select v-model="form.moduleId" placeholder="请选择所属模块" style="width: 200px;" >
            <el-option
                v-for="module in moduleDataList"
                :key="module.id"
                :label="module.name"
                :value="module.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="关卡编码" prop="levelCode">
          <el-input v-model="form.levelCode" placeholder="请输入关卡编码" />
        </el-form-item>

        <el-form-item label="关卡类型" prop="levelType">
          <el-radio-group v-model="form.levelType">
            <el-radio
                v-for="dict in level_type"
                :key="dict.value"
                :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="关卡标题" prop="levelTitle">
          <el-input v-model="form.levelTitle" placeholder="请输入关卡标题" />
        </el-form-item>
        <el-form-item label="关卡图片" prop="imgUrl">
          <image-upload v-model="form.imgUrl"/>
        </el-form-item>
        <el-form-item label="关卡信息" prop="levelInfo">
          <editor v-model="form.levelInfo" :min-height="192"/>
        </el-form-item>

      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Level">
import { listLevel, getLevel, delLevel, addLevel, updateLevel } from "@/api/project/level"
import { getModuleDataList } from "@/api/project/module.js"

const { proxy } = getCurrentInstance()
const { level_type } = proxy.useDict('level_type')
const levelList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const moduleDataList = ref([])
const moduleTypeParams = ref(2);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    moduleId: null,
    levelCode: null,
    levelTitle: null,
  },
  rules: {
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询关卡列表 */
function getList() {
  loading.value = true
  listLevel(queryParams.value).then(response => {
    levelList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}
/** 查询基础闯关模块列表 */
function initModuleDataList() {
  loading.value = true
  getModuleDataList(moduleTypeParams.value).then(response => {
    moduleDataList.value = response.data
    loading.value = false
  })
}
// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    moduleId: null,
    levelCode: null,
    levelTitle: null,
    levelInfo: null,
    imgUrl: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null
  }
  proxy.resetForm("levelRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加关卡"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _id = row.id || ids.value
  getLevel(_id).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改关卡"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["levelRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateLevel(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addLevel(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value
  proxy.$modal.confirm('是否确认删除关卡编号为"' + _ids + '"的数据项？').then(function() {
    return delLevel(_ids)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('project/level/export', {
    ...queryParams.value
  }, `level_${new Date().getTime()}.xlsx`)
}

getList();
initModuleDataList();
</script>
