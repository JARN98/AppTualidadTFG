import { Noticia } from '.'

let noticia

beforeEach(async () => {
  noticia = await Noticia.create({ title: 'test', description: 'test', likes: 'test', comentarios: 'test', localizacion: 'test', photos: 'test', autor: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = noticia.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(noticia.id)
    expect(view.title).toBe(noticia.title)
    expect(view.description).toBe(noticia.description)
    expect(view.likes).toBe(noticia.likes)
    expect(view.comentarios).toBe(noticia.comentarios)
    expect(view.localizacion).toBe(noticia.localizacion)
    expect(view.photos).toBe(noticia.photos)
    expect(view.autor).toBe(noticia.autor)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = noticia.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(noticia.id)
    expect(view.title).toBe(noticia.title)
    expect(view.description).toBe(noticia.description)
    expect(view.likes).toBe(noticia.likes)
    expect(view.comentarios).toBe(noticia.comentarios)
    expect(view.localizacion).toBe(noticia.localizacion)
    expect(view.photos).toBe(noticia.photos)
    expect(view.autor).toBe(noticia.autor)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})
