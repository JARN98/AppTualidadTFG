import request from 'supertest'
import { apiRoot } from '../../config'
import express from '../../services/express'
import routes, { Noticia } from '.'

const app = () => express(apiRoot, routes)

let noticia

beforeEach(async () => {
  noticia = await Noticia.create({})
})

test('POST /noticias 201', async () => {
  const { status, body } = await request(app())
    .post(`${apiRoot}`)
    .send({ title: 'test', description: 'test', likes: 'test', comentarios: 'test', localizacion: 'test', photos: 'test', autor: 'test' })
  expect(status).toBe(201)
  expect(typeof body).toEqual('object')
  expect(body.title).toEqual('test')
  expect(body.description).toEqual('test')
  expect(body.likes).toEqual('test')
  expect(body.comentarios).toEqual('test')
  expect(body.localizacion).toEqual('test')
  expect(body.photos).toEqual('test')
  expect(body.autor).toEqual('test')
})

test('GET /noticias 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}`)
  expect(status).toBe(200)
  expect(Array.isArray(body.rows)).toBe(true)
  expect(Number.isNaN(body.count)).toBe(false)
})

test('GET /noticias/:id 200', async () => {
  const { status, body } = await request(app())
    .get(`${apiRoot}/${noticia.id}`)
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(noticia.id)
})

test('GET /noticias/:id 404', async () => {
  const { status } = await request(app())
    .get(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})

test('PUT /noticias/:id 200', async () => {
  const { status, body } = await request(app())
    .put(`${apiRoot}/${noticia.id}`)
    .send({ title: 'test', description: 'test', likes: 'test', comentarios: 'test', localizacion: 'test', photos: 'test', autor: 'test' })
  expect(status).toBe(200)
  expect(typeof body).toEqual('object')
  expect(body.id).toEqual(noticia.id)
  expect(body.title).toEqual('test')
  expect(body.description).toEqual('test')
  expect(body.likes).toEqual('test')
  expect(body.comentarios).toEqual('test')
  expect(body.localizacion).toEqual('test')
  expect(body.photos).toEqual('test')
  expect(body.autor).toEqual('test')
})

test('PUT /noticias/:id 404', async () => {
  const { status } = await request(app())
    .put(apiRoot + '/123456789098765432123456')
    .send({ title: 'test', description: 'test', likes: 'test', comentarios: 'test', localizacion: 'test', photos: 'test', autor: 'test' })
  expect(status).toBe(404)
})

test('DELETE /noticias/:id 204', async () => {
  const { status } = await request(app())
    .delete(`${apiRoot}/${noticia.id}`)
  expect(status).toBe(204)
})

test('DELETE /noticias/:id 404', async () => {
  const { status } = await request(app())
    .delete(apiRoot + '/123456789098765432123456')
  expect(status).toBe(404)
})
