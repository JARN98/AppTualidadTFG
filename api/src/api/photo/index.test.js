import request from 'supertest'
import { apiRoot } from '../../config'
import express from '../../services/express'
import routes, { Photo } from '.'

const app = () => express(apiRoot, routes)

let photo

beforeEach(async () => {
  photo = await Photo.create({})
})

test('POST /photos 201', async () => {
  const { status, body } = await request(app())
    .post(`${apiRoot}`)
    .send({ noticia: 'test', imgurLink: 'test', deleteHash: 'test' })
  expect(status).toBe(201)
  expect(typeof body).toEqual('object')
  expect(body.noticia).toEqual('test')
  expect(body.imgurLink).toEqual('test')
  expect(body.deleteHash).toEqual('test')
})

test('GET /photos 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}`)
  expect(status).toBe(200)
  expect(Array.isArray(body.rows)).toBe(true)
  expect(Number.isNaN(body.count)).toBe(false)
})

test('GET /photos/:id 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}/${photo.id}`)
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(photo.id)
})

test('GET /photos/:id 404', async () => {
  const { status } = await request(app())
    .get(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})

test('PUT /photos/:id 200', async () => {
  const { status, body } = await request(app())
    .put(`${apiRoot}/${photo.id}`)
    .send({ noticia: 'test', imgurLink: 'test', deleteHash: 'test' })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(photo.id)
  expect(body.noticia).toEqual('test')
  expect(body.imgurLink).toEqual('test')
  expect(body.deleteHash).toEqual('test')
})

test('PUT /photos/:id 404', async () => {
  const { status } = await request(app())
    .put(apiRoot + '/123456789098765432123456')
    .send({ noticia: 'test', imgurLink: 'test', deleteHash: 'test' })
  expect(status).toBe(404)
})

test('DELETE /photos/:id 204', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${photo.id}`)
  expect(status).toBe(204)
})

test('DELETE /photos/:id 404', async () => {
  const { status } = await request(app())
    .delete(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})
