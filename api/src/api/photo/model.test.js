import { Photo } from '.'

let photo

beforeEach(async () => {
  photo = await Photo.create({ noticia: 'test', imgurLink: 'test', deleteHash: 'test' })
})

describe('view', () => {
  it('returns simple view', () => {
    const view = photo.view()
    expect(typeof view).toBe('object')
    expect(view.id).toBe(photo.id)
    expect(view.noticia).toBe(photo.noticia)
    expect(view.imgurLink).toBe(photo.imgurLink)
    expect(view.deleteHash).toBe(photo.deleteHash)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })

  it('returns full view', () => {
    const view = photo.view(true)
    expect(typeof view).toBe('object')
    expect(view.id).toBe(photo.id)
    expect(view.noticia).toBe(photo.noticia)
    expect(view.imgurLink).toBe(photo.imgurLink)
    expect(view.deleteHash).toBe(photo.deleteHash)
    expect(view.createdAt).toBeTruthy()
    expect(view.updatedAt).toBeTruthy()
  })
})
